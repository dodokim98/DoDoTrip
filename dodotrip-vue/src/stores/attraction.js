import { ref } from "vue";
import { defineStore } from "pinia";

export const useAttractionPlanStore = defineStore("attractionPlanStore", () => {
  // // 추가한 여행지를 담을 배열
  const selectedTrips = ref([]);

  function addTrip(place) {
    this.selectedTrips.push(place);
  }
  function removeTrip(index) {
    this.selectedTrips.splice(index, 1);
  }
  function clearSelectedTrips() {
    this.selectedTrips.value = [];
  }

  //API 호출 - PlanAttraction DB에 추가 이후에 selectedTrips 배열 초기화

  //   function insertAttractionTrip()

  return {
    selectedTrips,
    addTrip,
    removeTrip,
    clearSelectedTrips,
  };
});
