<script setup>
import QnAFormItem from './item/QnAFormItem.vue';
</script>


<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 text-center">
            글쓰기
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <QnAFormItem type="regist" />
      </div>
    </div>
  </div>
</template>

<style scoped>
*{
  font-family: "Noto Sans KR", sans-serif;
}

.container {
  background-color: #f2f2f2; /* Light gray background */
  max-width: 1140px;
  margin-top: 2%;
}

h2 {
  background-color: #343a40; /* Dark gray background for the title */
  color: #f8f9fa; /* Light text color for the title */
  padding: 10px;
  border-radius: 5px;
}

/* Style for the form items */
.form-group {
  margin-bottom: 20px;
}

label {
  color: #343a40; /* Dark gray color for form labels */
}

.input-group {
  width: 100%;
}

input {
  width: 100%;
  padding: 10px;
  border: 1px solid #343a40; /* Dark gray border for input fields */
  border-radius: 5px;
}

textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #343a40; /* Dark gray border for textarea */
  border-radius: 5px;
}

.btn-primary {
  background-color: #007bff; /* Blue color for the submit button */
  color: #fff; /* White text color for the submit button */
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.btn-primary:hover {
  background-color: #0056b3; /* Darker blue color on hover */
}
</style>
