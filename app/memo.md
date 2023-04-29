### 1. Multipart를 활용해서 API Call로 이미지 보내기
   (참고 : https://velog.io/@dev_thk28/Android-Retrofit2-Multipart%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0-Java#%EC%97%AC%EB%9F%AC%EA%B0%9C%EC%9D%98-file)

```kotlin
@Multipart
@POST("주소")
suspend fun registerPets(
// 이미지는 MultipartBody.Part 타입
@Part("files") files: List<MultipartBody.Part>,
// 나머지 데이터는 RequestBody에 묶어서
@Part("otherData") other_data: RequestBody,
...
): Response<응답 형태>
```

### 2. MultipartBody.Part에 어떤 타입으로 데이터를 넣어야 하는가?
: File Type을 넣어줘야 한다. 따라서 받아온 Uri -> File로 변환해야 한다.

```java
// 여러 file들을 담아줄 ArrayList
ArrayList<MultipartBody.Part> files = new ArrayList<>();

// 파일 경로들을 가지고있는 `ArrayList<Uri> filePathList`가 있다고 칩시다...
for (int i = 0; i < filePathList.size(); ++i) {
   // Uri 타입의 파일경로를 가지는 RequestBody 객체 생성
   RequestBody fileBody = RequestBody.create(MediaType.parse("image/jpeg"), filePathList.get(i));

    // 사진 파일 이름
    String fileName = "photo" + i + ".jpg";
    // RequestBody로 Multipart.Part 객체 생성
    MultipartBody.part filePart = Multipart.Part.createFormData("photo", fileName, fileBody);
    
    // 추가
    files.add(filePart);
}
```

### 3. 그러면 어떻게?
a. pets ArrayList가 있을테니, pets에 있는 Uri들을 전부 File로 변환한다.
b. a에서 변환한 File들을 API에 넣어서 실행.

### 4. 팁
- 안드로이드 10 전후에서 이미지의 Uri 처리가 바뀐 게 있어서, File이나 Multipart 변환 시 오류가 나는 경우가 있었다.
- 만약 Uri를 다른 타입으로 변환할 때 코드에 문제가 없음에도 오류가 난다면, 테스트한 폰의 안드로이드 버전을 포함해서 검색하는 것을 추천합니다.