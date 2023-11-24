import { ref } from "vue";
import { defineStore } from "pinia";

export const useAttractionDetailStore = defineStore("attractionDetailStore", () => {
  const updatedTripListItem = ref(null);

  return {
    updatedTripListItem,
  };
});
