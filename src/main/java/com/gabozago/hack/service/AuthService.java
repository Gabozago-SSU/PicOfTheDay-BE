package com.gabozago.hack.service;

import com.gabozago.hack.domain.User;
import com.gabozago.hack.dto.UserDto;
import com.gabozago.hack.repository.place.UserRepo;
import lombok.RequiredArgsConstructor;

import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
@Service
@RequiredArgsConstructor
@Transactional
@PropertySource("classpath:application-oauth.properties")
public class AuthService {
    private final UserRepo userRepository;
    private final S3Uploader s3Uploader;

    @Value("${GOOGLE_SNS_BASE_URL}")
    private String GOOGLE_SNS_BASE_URL;

    @Value("${GOOGLE_SNS_CLIENT_ID}")
    private String GOOGLE_SNS_CLIENT_ID;

    @Value("${GOOGLE_SNS_CALLBACK_URL}")
    private String GOOGLE_SNS_CALLBACK_URL;

    @Value("${GOOGLE_SNS_CLIENT_SECRET}")
    private String GOOGLE_SNS_CLIENT_SECRET;

    @Value("${GOOGLE_SNS_TOKEN_BASE_URL}")
    private String GOOGLE_SNS_TOKEN_BASE_URL;

    public ResponseEntity setNickname(UserNicknameDto userNicknameDto){
        User user = userRepository.findById(userNicknameDto.getUserId())
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));

        user.setName(userNicknameDto.getNickname());
        userRepository.save(user);
        return new ResponseEntity(HttpStatus.OK);
    }

    public String requestAccessToken(String code){
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, params, String.class);

        if(responseEntity.getStatusCode() != HttpStatus.OK){
            return "구글 로그인 요청 실패";
        }

        JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        String access_token = jsonObject.getString("access_token");
        return access_token;
    }

    public String getGoogleSnsId(String code){
        String access_token = requestAccessToken(code);
        try {
            URL url = new URL("https://www.googleapis.com/oauth2/v2/userinfo?access_token=" + access_token);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while((line = br.readLine()) != null){
                sb.append(line);
            }

            JSONObject userInfo = new JSONObject(sb.toString());
            String snsId = userInfo.getString("id");

            return snsId;

        }catch(Exception e){
            return "";
        }
    }

    public Long getUserIdByCode(String code){
        String snsId = getGoogleSnsId(code);
        User user = userRepository.findBySnsId(snsId)
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));

        return user.getId();
    }

    public ResponseEntity setProfileImage(UserProfileImageDto userProfileImageDto, MultipartFile multipartFile) throws IOException {

        User user = userRepository.findById(userProfileImageDto.getUserId())
                .orElseThrow(() -> new IllegalStateException("그런 유저 없음"));

        String[] value = s3Uploader.upload(multipartFile, "user");
        user.setProfileImage(value[1]); // 이미지 url 저장?
        userRepository.save(user);

        return new ResponseEntity(HttpStatus.OK);
    }
}
