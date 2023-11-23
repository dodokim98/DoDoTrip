<script setup>
import { RouterView } from "vue-router";
import TheHeadingNavbar from "@/components/layout/TheHeadingNavbar.vue";
import TheFooter from "./components/layout/TheFooter.vue";
</script>

<template>
  <div class="app-container">
    <TheHeadingNavbar></TheHeadingNavbar>
    <router-view></router-view>
    <TheFooter></TheFooter>
  </div>
</template>

<style>
@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap"); /* Noto Sans Font */
* {
  font-family: "Noto Sans KR", sans-serif;
}

.app-container {
  display: flex;
  flex-direction: column;
  min-height: 100vh; /* Make sure the container takes at least the full height of the viewport */
}
</style>
