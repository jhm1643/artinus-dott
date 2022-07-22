1. 인증서버 구현
   1. SPEC : java(11), spring boot(2.x), h2database, jpa, spring security, jjwt 
   2. Rest Api 형태의 인증서버를 구현하기 위해 session이 아닌 jwt-token을 활용한 인증서버를 구현
   3. 테이블은 사용자 테이블과 블랙리스트(로그아웃) 관리 테이블 두개로 구성
   4. 회원가입(singUp) API를 통해 사용자 등록(디폴트로 일반 권한으로 등록됨)
   5. 로그인(signIn) API를 통해 등록된 사용자 정보인 이메일과 패스워드를 인증받고 아이디와 권한정보가 담긴 token을 발급 받는다.
      1. 로그인 API 자체에서 인가를 수행한다긴 보다 발급받은 토큰으로 다른 API 호출 시 인가가 진행되는 형태
   6. 로그아웃(singOut) API를 통해 로그아웃은 사용자의 정보가 블랙리스트에 저장이되고 해당 사용자의 정보가 담긴 토큰은 사용이 불가능하게 되고 이후 로그인 시 블랙리스트에서 해당 사용자의 정보가 삭제된다. 
      1. 사실 로그아웃 API의 요구사항에서 인가된 정보를 파기한다고 되어있어 session구조로 가려고 했으나.. API만으로 개발되는 특성상 jwt를 활용하게 되었다..
 
2. swagger url : http://localhost:8080/swagger-ui/index.html