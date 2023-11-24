<script setup>
import BoardFormItem from "./item/BoardFormItem.vue";
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 bg-dark text-center text-light">
           글수정
        </h2>
      </div>
      <div class="col-lg-10 text-start bg-gray p-3">
        <BoardFormItem type="modify" />
      </div>
    </div>
  </div>
</template>

<style scoped>
.bg-dark {
  background-color: #343a40 !important;
}

.text-light {
  color: #ffffff !important;
}

.bg-gray {
  background-color: #f2f2f2 !important;
}
</style>