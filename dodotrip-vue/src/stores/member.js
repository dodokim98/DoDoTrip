import { ref } from "vue";
import { useRouter } from "vue-router";
import { defineStore } from "pinia";
import { jwtDecode } from "jwt-decode";
import { useCookies } from "vue3-cookies";

import {
  userConfirm,
  findByEmail,
  tokenRegeneration,
  logout,
} from "@/api/user";
import { httpStatusCode } from "@/util/http-status";
const { cookies } = useCookies();

export const useMemberStore = defineStore("memberStore", () => {
  const router = useRouter();

  const isLogin = ref(false);
  const isLoginError = ref(false);
  const userInfo = ref(null);
  const isValidToken = ref(false);
  const userRole = ref(null);

  const userLogin = async (loginUser) => {
    await userConfirm(
      loginUser,
      (response) => {
        if (response.status === httpStatusCode.CREATE) {
          let { data } = response;
          let accessToken = data["access-token"];
          let refreshToken = data["refresh-token"];
          console.log("accessToken", accessToken);
          console.log("refreshToken", refreshToken);
          userRole.value = response.data.role;

          isLogin.value = true;
          isLoginError.value = false;
          isValidToken.value = true;

          sessionStorage.setItem("accessToken", accessToken);
          sessionStorage.setItem("refreshToken", refreshToken);
          console.log("sessiontStorage에 담았다", isLogin.value);

          cookies.set("id", loginUser.userEmail, Date.now());

        } else {
          console.log("로그인 실패했다");
          isLogin.value = false;
          isLoginError.value = true;
          isValidToken.value = false;
        }
      },
      (error) => {
        console.error(error);
      },
      {persist: true}
    );
  };
  const getUserInfo = async (token) => {
    let decodeToken = jwtDecode(token);
    console.log("2. decodeToken", decodeToken);
    userRole.value = decodeToken.userRole;
    console.log(decodeToken.userEmail);

    await findByEmail(
      decodeToken.userEmail,
      (response) => {
        if (response.status === httpStatusCode.OK) {
          userInfo.value = response.data.userInfo;
          console.log("3. getUserInfo data >> ", response.data);
        } else {
          console.log("유저 정보 없음!!!!");
        }
      },
      async (error) => {
        console.log(error);
        console.error(
          "getUserInfo() error code [토큰 만료되어 사용 불가능.] ::: ",
          error.response.status
        );
        isValidToken.value = false;

        await tokenRegenerate(); //토큰 재발급
      }
    );
    return userInfo.value;
  };

  const tokenRegenerate = async () => {
    console.log(
      "토큰 재발급 >> 기존 토큰 정보 : {}",
      sessionStorage.getItem("accessToken")
    );
    await tokenRegeneration(
      JSON.stringify(userInfo.value),
      (response) => {
        if (response.data.status === httpStatusCode.CREATE) {
          let accessToken = response.data["access-token"];
          console.log("재발급 완료 >> 새로운 토큰 : {}", accessToken);
          sessionStorage.setItem("accessToken", accessToken);
          isValidToken.value = true;
        }
      },
      async (error) => {
        // HttpStatus.UNAUTHORIZE(401) : RefreshToken 기간 만료 >> 다시 로그인!!!!
        console.log("error: {}", error.response);
        if (error.response.status === httpStatusCode.UNAUTHORIZED) {
          console.log("갱신 실패");
          // 다시 로그인 전 DB에 저장된 RefreshToken 제거.
          await logout(
            userInfo.userEmail,
            (response) => {
                console.log(response);
              if (response.status === httpStatusCode.OK) {
                console.log("리프레시 토큰 제거 성공");
              } else {
                console.log("리프레시 토큰 제거 실패");
              }
              alert("RefreshToken 기간 만료!!! 다시 로그인해 주세요.");
              isLogin.value = false;
              userInfo.value = null;
              isValidToken.value = false;
              router.push({ name: "user-login" });
            },
            (error) => {
              console.error(error);
              isLogin.value = false;
              userInfo.value = null;
            }
          );
        }
      }
    );
  };

  const userLogout = async (userEmail) => {
    await logout(
      userEmail,
      (response) => {
        let msg = "로그아웃 중 에러 발생했습니다..";
        if (response.status === httpStatusCode.OK) {
          isLogin.value = false;
          userInfo.value = null;
          isValidToken.value = false;

          sessionStorage.removeItem("accessToken");
          sessionStorage.removeItem("refreshToken");
          cookies.remove("id");
          console.log("Logout successful");

          msg = "로그아웃 되었습니다.";
          alert(msg);
        } 
      },
      (error) => {
        console.log(error);
      }
    );
  };

  return {
    isLogin,
    isLoginError,
    userInfo,
    isValidToken,
    userRole,
    userLogin,
    getUserInfo,
    tokenRegenerate,
    userLogout,
  };
});
