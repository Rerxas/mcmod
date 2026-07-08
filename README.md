# Always Survival HUD (Fabric 1.21.1)

크리에이티브 모드에서도 체력바, 배고픔바, 경험치/인챈트 바 등 서바이벌 HUD를 항상 표시해주는 Fabric 모드입니다.

## 원리
`ClientPlayerInteractionManager#hasStatusBars()` 메서드가 서바이벌/어드벤처 모드에서만
true를 반환해서 크리에이티브에서는 체력/배고픔 바가 그려지지 않습니다.
이 모드는 Mixin으로 이 메서드를 가로채서, 스펙테이터 모드가 아닌 이상 항상 true를 반환하게 만듭니다.
(경험치 바는 원래도 스펙테이터가 아니면 표시되므로 따로 손대지 않았습니다.)

클라이언트 사이드 전용 모드라서 서버에 설치할 필요 없이 내 클라이언트에만 넣으면 됩니다
(싱글플레이어/로컬 크리에이티브에서 바로 작동).

## ⚠️ 빌드 관련 안내
이 프로젝트는 **소스 코드만** 포함되어 있고, 컴파일된 `.jar` 파일은 없습니다.
Minecraft/Fabric 라이브러리를 다운로드하려면 인터넷 접속이 필요한데,
지금 이 코드를 만든 환경(샌드박스)에서는 Mojang/Fabric 서버 접속이 막혀 있어서
제가 직접 빌드까지 해드릴 수는 없었습니다. 대신 아래 순서대로 하시면 사용자님 PC에서
5분 안에 직접 빌드할 수 있습니다.

## 🌟 가장 쉬운 방법: GitHub Actions로 클라우드 빌드 (컴퓨터에 아무것도 설치 안 해도 됨)

이 프로젝트에는 이미 `.github/workflows/build.yml`이 포함되어 있어서,
GitHub에 업로드만 하면 GitHub 서버가 알아서 빌드해줍니다.

1. [github.com](https://github.com)에서 무료 계정 만들기 (이미 있으면 로그인)
2. 오른쪽 위 **+** 버튼 → **New repository** 클릭
   - Repository name: `always-hud` (아무 이름이나 가능)
   - Public/Private 아무거나 선택
   - **Create repository** 클릭
3. 생성된 빈 저장소 페이지에서 **"uploading an existing file"** 링크 클릭
   (또는 `Add file → Upload files` 버튼)
4. 압축 푼 `always-hud` 폴더 안의 **모든 파일/폴더를 통째로 드래그 앤 드롭**
   (`.github` 폴더, `src` 폴더, `build.gradle` 등 전부 다)
5. 아래 **Commit changes** 버튼 클릭 (업로드 완료)
6. 저장소 상단 메뉴에서 **Actions** 탭 클릭
7. "Build Fabric Mod" 워크플로가 자동으로 실행 중일 거예요 (노란 점 → 초록 체크로 바뀔 때까지 대기, 보통 2~4분)
8. 초록 체크가 뜨면 그 워크플로 실행 클릭 → 페이지 맨 아래 **Artifacts** 섹션에서
   `always-hud-jar` 다운로드 (zip 파일로 받아짐, 그 안에 진짜 `.jar`가 들어있음)
9. 압축 풀면 `always-hud-1.0.0.jar` 나옴 → 이걸 `.minecraft/mods` 폴더에 넣기

이 방법이면 로컬에 Java, Gradle, IntelliJ 아무것도 설치할 필요가 없습니다.
빌드가 실패하면 Actions 탭 로그에 에러가 그대로 나오니 캡처해서 보여주시면 도와드릴게요.

---

## 로컬에서 직접 빌드하는 방법

### 준비물
- **Java 21** (JDK) 설치 — [Adoptium](https://adoptium.net/) 등에서 다운로드
- 인터넷 연결 (Minecraft/Fabric 라이브러리 자동 다운로드용)

### 방법 A: IntelliJ IDEA 사용 (추천, 제일 쉬움)
1. IntelliJ IDEA (Community 버전 무료)로 이 폴더(`always-hud`)를 엽니다.
2. Gradle 프로젝트로 자동 인식되며, wrapper가 없으면 IntelliJ가 자동으로 생성/동기화합니다.
   (오른쪽 Gradle 탭 → 코끼리 아이콘 눌러서 새로고침)
3. 오른쪽 Gradle 탭에서 `Tasks > build > build` 더블클릭
   (또는 터미널에서 `./gradlew build` 실행)
4. 빌드가 끝나면 `build/libs/always-hud-1.0.0.jar` 파일이 생성됩니다.
   (`-sources.jar`, `-dev.jar`는 필요 없고, 이 파일만 사용하면 됩니다)

### 방법 B: 터미널 + Gradle 직접 사용
```bash
# gradle이 설치되어 있다면 (없으면 https://gradle.org/install 참고)
cd always-hud
gradle wrapper --gradle-version 8.8
./gradlew build      # Windows는 gradlew.bat build
```
빌드 완료 후 `build/libs/always-hud-1.0.0.jar` 확인.

### 설치
1. 이미 **Fabric Loader**와 **Fabric API**가 설치되어 있어야 합니다 (Minecraft 1.21.1용).
2. 위에서 만든 `always-hud-1.0.0.jar`를 `.minecraft/mods` 폴더에 복사합니다.
3. Fabric 프로필로 게임 실행 → 크리에이티브 모드로 들어가면 체력/배고픔/경험치 바가 그대로 보입니다.

## 빌드가 실패한다면
`gradle.properties`에 적힌 버전들(`yarn_mappings`, `loader_version`, `fabric_version`)이
시간이 지나면서 구버전이 될 수 있습니다. 이럴 땐
[fabricmc.net/develop](https://fabricmc.net/develop/) 에서 Minecraft 1.21.1에 맞는
최신 버전 번호를 확인해서 `gradle.properties`를 수정해주세요.
