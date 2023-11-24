// import "./assets/main.css";
import { createApp } from "vue";
import App from "./App.vue";
import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";
import VueDatePicker from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";
import router from "./router";

import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";
import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
// import specific icons
import { fas } from "@fortawesome/free-solid-svg-icons";
import { far } from "@fortawesome/free-regular-svg-icons";
import { fab } from "@fortawesome/free-brands-svg-icons";
import { useMemberStore } from "@/stores/member";
import { useCookies } from "vue3-cookies";
import { useMenuStore } from "@/stores/menu";

// add icons to the library
library.add(fas, far, fab);

const app = createApp(App);
const pinia = createPinia();

pinia.use(piniaPluginPersistedstate);

app.component("VueDatePicker", VueDatePicker);
app.component("font-awesome-icon", FontAwesomeIcon);
app.use(pinia);
app.use(router);

const { changeMenuState } = useMenuStore();
const { cookies } = useCookies();
const memberStore = useMemberStore();

const initializeAuthenticationState = async () => {
  if (memberStore.isLogin || cookies.get("id") !== null) {
    changeMenuState();
  }
};
initializeAuthenticationState().then(() => {
  app.mount("#app");
});
