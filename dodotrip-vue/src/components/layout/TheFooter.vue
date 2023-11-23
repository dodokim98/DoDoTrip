<script setup>
import { onMounted, onBeforeUnmount } from 'vue';

let isScrolled = false;

const handleScroll = () => {
  isScrolled = window.scrollY > 0;
};

onMounted(() => {
  window.addEventListener("scroll", handleScroll);
});

onBeforeUnmount(() => {
  window.removeEventListener("scroll", handleScroll);
});
</script>

<template>
  <footer :class="{ 'scrolled': isScrolled }" class="footer">
    <div class="footer-content">
      <span class="brand">DoDoTrip</span><br>
      <span class="copyright">© 2023</span>
    </div>
  </footer>
</template>

<style scoped>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css');
.footer {
  margin-top: auto; /* Push the footer to the bottom */
  width: 100%;
  text-align: center;
  background-color: #f8f8f8;
  padding: 10px;
  transition: margin-top 0.3s;
}

.scrolled {
  margin-top: 0; /* Set margin-top to 0 when scrolled */
}

.footer-content {
  font-family: 'Roboto', sans-serif;
  font-weight: 500;
}

.brand {
  font-size: 24px;
  color: rgba(0, 0, 0, 1);
}

.copyright {
  font-size: 14px;
  color: rgba(0, 0, 0, 1);
}
</style>
