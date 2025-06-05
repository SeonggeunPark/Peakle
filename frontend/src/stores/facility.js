import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'

const REST_API_URL = 'http://localhost:8080/facility'

export const useFacilityStore = defineStore('facility', () => {
  const facilList = ref([])
  const crowd  = ref([])
  const facilNames = ref([])

  // 시설 정보 요청
  const getFacilList = async function () {
    try {
      axios.get(`${REST_API_URL}`)
        .then((res) => {
          facilList.value = res.data
          
          // 시설 정보를 성공적으로 받으면 혼잡도 데이터를 추가 요청
          axios.get(`${REST_API_URL}/crowd`)
            .then((res) => {
              if (res.data && typeof res.data === 'object') {
                const crowds = res.data; // { "1": 1.2, "2": 2.2, "3": 3.0, "4": 2.1 }
                const keys = Object.keys(crowds); // ['1', '2', '3', '4']
  
                for (let i = 0; i < keys.length; i++) {
                  const key = parseInt(keys[i]); // 문자열 키를 숫자로 변환
                  let idx = facilList.value.findIndex((facil) => {
                    return facil.id === key; // 숫자로 비교
                  });
  
                  facilList.value[idx].crowd = crowds[keys[i]]; // crowd 값 업데이트
                }
              } else {
                console.error('res.data가 유효한 객체가 아닙니다:', res.data);
              }
            })
            .catch((err) => {
              console.error('API 호출 중 오류 발생:', err);
            });
        })
        .catch((err) => {
          console.log('err')
        })
    } catch {
      console.log('실패')
    }
  }

  const facilDetail = ref({})
  const getFacilDetail = function(id) {
    axios.get(`${REST_API_URL}/detail/${id}`)
    .then((res)=>{
      facilDetail.value = res.data
    })
    .catch((err)=>{
      console.log(err)
    })
  }

  const crowdCreate = function(facilityId, userId, evaluate, manager) {
    let rating = 1;
    if (evaluate === "혼잡") {
      rating = 3;
    } else if (evaluate === "원활") {
      rating = 2;
    }
    axios.post(`${REST_API_URL}/crowd`, {
      facilityId: facilityId,
      userId: userId,
      rating: rating,
      manager: manager
    })
    .then((res)=>{
      console.log(res)
    })
    .catch((err)=>{
      console.log(err)
    })
  }

  const getFacilNames = function () {
    axios.get(`${REST_API_URL}`)
        .then((res) => {
          facilNames.value = res.data.map(item => item.name);
        })
        .catch((err) =>{
          console.log(err)
        })
  }
  
  return { facilList, getFacilList, facilDetail, getFacilDetail, crowd, crowdCreate, facilNames, getFacilNames }
})
