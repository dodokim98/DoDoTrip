<script setup>
import { ref } from "vue";
import VueDatePicker from "@vuepic/vue-datepicker";
import "@vuepic/vue-datepicker/dist/main.css";
import { ko } from "date-fns/locale";
import { defineRefs } from "@/util/helper.js";
import { useAttractionPlanStore } from "@/stores/attraction";

const store = useAttractionPlanStore();
const { current, from, to } = store;

const locale = ref(ko);
const inputFormat = ref("yyyy-MM-dd");

let oldVal = "";

const inputs = defineRefs(["from", "to", "current", "dp3"]);

const clickCalIcon = (refId) => {
  const dp = inputs[refId].value;
  dp.inputRef.focus();
};

const getCalValue = (refId) => {
  const dp = inputs[refId].value;
  alert(dp.input);
};

const setOldValue = (event) => {
  oldVal = event.target.value;
};

const setCurrent = (value) => {
  current.value = value;
}

const date = ref();

// const dateClicked = (date) => {
//   console.log(`Selected ${date}`);
// }

// const validateFromTo = (target, refFrom, refTo) => {
//   setTimeout(() => {
//     const dpFrom = inputs[refFrom].value;
//     const dpTo = inputs[refTo].value;

//     if (dpFrom.input > dpTo.input) {
//       alert("Validation Error!!");

//       let date = null;
//       if (oldVal) {
//         const arrOldVal = oldVal.split("-");
//         date = new Date(
//           Number(arrOldVal[0]),
//           Number(arrOldVal[1]) - 1,
//           Number(arrOldVal[2])
//         );
//       }
//       console.log(date);
//       if (target === "from") {
//         store.from.value = date;
//       } else if (target === "to") {
//         store.to.value = date;
//       }

//       return;
//     }
//   }, 10);
// };

const validateFromTo = (target, refFrom, refTo) => {
  const dpFrom = inputs[refFrom].value;
  const dpTo = inputs[refTo].value;

  if (dpFrom.input > dpTo.input) {
    alert("Validation Error!!");

    let date = null;
    if (oldVal) {
      const arrOldVal = oldVal.split("-");
      date = new Date(
        Number(arrOldVal[0]),
        Number(arrOldVal[1]) - 1,
        Number(arrOldVal[2])
      );
    }

    // $nextTick을 사용하여 컴포넌트 업데이트가 완료된 후에 실행되도록 합니다.
    store.$nextTick(() => {
      console.log(date);
      if (target === "from") {
        from.value = date;
      } else if (target === "to") {
        to.value = date;
      }
    });
  }
};

const isTodayOver = (date) => {
  return date > new Date();
};
</script>

<template>
  <div style="width: 100%">
    <div class="date">
      <span>From:</span>
      <div @click="clickCalIcon('from')">
        <VueDatePicker
          v-model="from"
          :ref="inputs.from"
          class="datepicker"
          :locale="locale.value"
          :weekStartsOn="0"
          :inputFormat="inputFormat"
          @focus="setOldValue"
          @update:model-value="validateFromTo('from', 'from', 'to')"
        />
      </div>

      <!-- -------------TO------------------ -->

      <span style="margin-left: 20px">To:</span>
      <div @click="clickCalIcon('to')">
        <VueDatePicker
          v-model="to"
          :ref="inputs.to"
          class="datepicker"
          :locale="locale.value"
          :weekStartsOn="0"
          :inputFormat="inputFormat"
          @focus="setOldValue"
          @update:model-value="validateFromTo('to', 'from', 'to')"
        />
        <p v-if="to">Selected date: {{ to }}</p>
      </div>

    </div>

    <!-- -------------current--------------- -->
    <div
      style="margin-top: 5px;
        display: flex;
        justify-content: center;
        align-items: center;">

      <div @click="clickCalIcon('current')">
        <VueDatePicker
          v-model="current"
          :ref="inputs.current"
          class="datepicker"
          :locale="locale.value"
          :weekStartsOn="0"
          :inputFormat="inputFormat"
          :lower-limit="from"
          :upper-limit="to"
          :clearable="true"
          @update:model-value="setCurrent"
        />
      </div>
    </div>
    <p>{{current}}</p>
    <p>{{from}}</p>
    <p>{{to}}</p>

    <hr />
  </div>
</template>

<style scoped>
* {
  font-family: "Noto Sans KR", sans-serif;
}
div {
  text-align: center;
}
div.date {
  display: inline-flex;
}
</style>
