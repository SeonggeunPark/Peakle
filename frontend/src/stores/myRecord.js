import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/myrecord`

export const useMyRecordStore = defineStore('record', () => {
  const recordList = ref([])
  const record = ref({})
  const datetime = ref('')

  // 기록 전체 조회
  const getMyRecordList = function () {
    const sessionUser = sessionStorage.getItem('access-token');
    const token = sessionUser.split('.');
    const id = JSON.parse(atob(token[1]))['id'];
    axios.get(`${REST_API_URL}/0/` + id, {
      headers: {
        'access-token': sessionStorage.getItem('access-token')
      }
    })
      .then((res) => {
        if (res.status == 200) {
          recordList.value = res.data;
        } else {
          recordList.value = res.data;
        }
      })
      .catch((err) => {
        console.log(err)
      })
  }

  // 기간별 기록 조회
  const getMyRecordDate = function (id, date) {
    return new Promise((resolve, reject) => {
      axios
        .get(`${REST_API_URL}/date/${id}/${date}`, {
          headers: {
            "access-token": sessionStorage.getItem("access-token"),
          },
        })
        .then((res) => {
          if (res.status === 200) {
            resolve(res.data); // 데이터가 있는 경우 반환
          } else {
            resolve(null); // 데이터가 없는 경우 null 반환
          }
        })
        .catch((err) => {
          console.log(err);
          reject(err); // 실패 시 에러 반환
        });
    });
  }

  // 기록 단일 조회
  const getMyRecord = async function (id) {
    try {
      const response = await axios.get(`${REST_API_URL}/${id}`);
      record.value = { ...response.data };

      if (record.value.facilityId) {
        const facilityResponse = await axios.get(`http://localhost:8080/facility/${record.value.facilityId}`);
        record.value.facilityName = facilityResponse.data.name; // 반응형으로 동작
      }

    } catch (error) {
      console.error('데이터 로드 실패:', error);
    }
  };

  // 기록 작성
  const CreateMyRecord = function (recordData) {
    axios.post(`${REST_API_URL}`, {
      facilityId: recordData.facilityId,
      userId: recordData.userId,
      rating: recordData.rating,
      title: recordData.title,
      content: recordData.content,
      record: recordData.record,
      writer: recordData.writer,
      created: datetime.value,
    })
      .then((res) => {
        alert("기록 등록이 완료되었습니다.")
        router.replace({ name: "calender" })
      })
      .catch((err) => {
        console.log(err)
      })
  }

  // 기록 수정
  const updateMyRecord = function () {
    axios.put(REST_API_URL, record.value)
      .then(() => {
        router.push({ name: "myRecordDetail", params: { id: record.value.id } })
      })
      .catch((err) => {
        console.log(err);
      })
  }

  return {
    recordList, record, datetime,
    getMyRecordList, getMyRecordDate, getMyRecord, updateMyRecord, CreateMyRecord
  }
}, { persist: true })
