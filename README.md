# 오목 - TCP/IP NetWork
서버와 클라이언트로 구성한 오목게임입니다. <p>
메인 화면에서 ‘혼자 하기’, ‘같이 하기’, ‘설정’ 이라는 기능을 이용할 수 있습니다.

## Running the tests

1. 혼자 하기
```
사용된 알고리즘은 단순성을 지키도록 하였습니다.
상대 돌이 3개가 연달아 이어진 공격이 있는 경우 방어.
쌍삼이 되는 자리는 돌을 놓을 수 없습니다.
게임이 종료되면 reset() 함수를 이용해 바둑판을 초기화하며 다시 그래픽 함수를 이용해 재실행
```

2. 같이 하기
```
같이 하기 클릭 시, ip와 포트번호를 통해 서버와 연결.
처음에 닉네임을 설정 서버쪽으로 전송.
한 수 둘 때마다 서버쪽으로 게임 상황에 대한 정보를 OmokBoard 클래스를 통하여 전달.

먼저 시작하는 플레이어는 바둑판을 클릭해서 이벤트를 발생시킵니다.
이후에 이벤트 발생은 서버 쪽으로 정보가 넘어가게 되고 서버는 다시 같은 방에 있는 플레이어들에게 그 정보를 뿌려줍니다.
상대방 플레이어는 쓰레드 run()에 의해 그러한 과정이 포착됩니다.
이후에 공격 권한이 넘어가게 되고 상대방 플레이어가 마찬가지로 바둑판을 클릭해서 이벤트를 발생시키는 방식으로 진행됩니다.

-기능-
  플레이어 목록
  방 목록
  기권
  무르기 요청
  타임 오버
  대화
  이모티콘
  쌍삼 방지
```

3. 환경 설정
```
첫번 째, 간단하게 이미지 변수 명만 바꾸어서 바둑 돌 디자인을 고를 수 있도록 하였습니다. 

두번 째, 바둑돌이 바둑판이 놓아질때 효과음의 on/off기능을 넣었습니다.
```


## Screen Shot
1. 메인 화면(클라이언트)

![오목_클라메인](https://user-images.githubusercontent.com/33171227/61588907-e59e7500-abdd-11e9-9010-59569c236c09.JPG)
----------------------------------------------------------------------------------------------------------------------------------------
2. 혼자 하기

![오목_혼자](https://user-images.githubusercontent.com/33171227/61588929-25655c80-abde-11e9-9cff-30fa880a8cdc.JPG)
----------------------------------------------------------------------------------------------------------------------------------------
3. 같이 하기
- 대기 방

![오목_클라_같이_대기](https://user-images.githubusercontent.com/33171227/61588913-fcdd6280-abdd-11e9-8111-997747c82970.JPG)

- 게임 방

![오목_같이_경기](https://user-images.githubusercontent.com/33171227/61588898-cc95c400-abdd-11e9-96ec-b7af1dcc9807.JPG)
----------------------------------------------------------------------------------------------------------------------------------------

3. 환경 설정

![오목_클라_설정](https://user-images.githubusercontent.com/33171227/61588949-71180600-abde-11e9-96d7-342b86136a9a.JPG)

## Built With
* [Eclipse](https://www.eclipse.org/downloads/) - using for Java

## Authors
* **정명한** - coding
* **임주훈** - image 


