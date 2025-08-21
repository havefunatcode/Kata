# TDD 카타(Kata) 연습 프로젝트

이 프로젝트는 테스트 주도 개발(TDD) 방법론을 연습하기 위한 카타(Kata)를 구현하는 공간입니다.
프로젝트에서 진행하는 모든 카타는 [kata-log.rocks](https://kata-log.rocks) 사이트를 기반으로 합니다.

## 프로젝트 구조

이 프로젝트는 Gradle 멀티 모듈로 구성되어 있으며, 각각의 카타(Kata)는 `modules` 디렉토리 아래에 독립적인 모듈로 존재합니다.

- `modules/banking`: 은행 입출금 관련 카타 모듈
- `modules/bowling`: 볼링 게임 점수 계산 카타 모듈

모든 모듈에 공통적으로 적용되는 빌드 설정은 최상위 `build.gradle.kts` 파일에 정의되어 있어, 각 모듈의 `build.gradle.kts` 파일은 비어있습니다. (모듈별 특별한 설정이 필요한 경우에만 내용을 추가합니다.)

## 새로운 카타 추가하기

새로운 카타를 위한 모듈을 추가하는 방법은 간단합니다.

1. `modules` 디렉토리 안에 새로운 카타의 이름으로 디렉토리를 생성합니다. (예: `mkdir modules/fizz-buzz`)
2. Gradle 빌드 시스템이 자동으로 새 디렉토리를 모듈로 인식합니다.
3. `modules/fizz-buzz/src/main/kotlin` 와 `modules/fizz-buzz/src/test/kotlin` 같은 표준 디렉토리 구조에 소스 코드와 테스트 코드를 작성합니다.

## 시작하기

### 빌드
```shell
./gradlew build
```

### 테스트 실행
```shell
./gradlew test
```

## 참고 자료
* **TDD Katas:** [https://kata-log.rocks](https://kata-log.rocks)