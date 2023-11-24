import { ref } from "vue";
import { defineStore } from "pinia";

export const useMenuStore = defineStore("menuStore", () => {
  const menuList = ref([
    { name: "회원가입", show: true, routeName: "user-signup" },
    { name: "MyPage", show: false, routeName: "user-mypage" },
    { name: "Logout", show: false, routeName: "user-logout" },
    { name: "Login", show: true, routeName: "user-login" },
  ]);

  const changeMenuState = () => {
    menuList.value = menuList.value.map((item) => ({ ...item, show: !item.show }));
  };

  return {
    menuList,
    changeMenuState,
  };
});
