import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'

const REST_API_URL = `http://localhost:8080/review`

export const useReviewStore = defineStore('review', () => {
  const reviewList = ref([])
  const review = ref({})

// 리뷰 작성
const registReview = function(review) {
  axios({
    url: REST_API_URL,
    method: 'POST',
    data: review
  })
    .then(() => {
    })
    .catch((err) => {
      console.log(err)
    })
}
  
  // 리뷰 전체 조회
  const getReviewList = function () {
    const sessionUser = sessionStorage.getItem('access-token');
    const token = sessionUser.split('.');
    const id = JSON.parse(atob(token[1]))['id'];
    axios.get(`${REST_API_URL}/0/`+id, {
      headers: {
        'access-token': sessionStorage.getItem('access-token')
      }
    })
    .then((res) => {
      if (res.status == 200) {
        reviewList.value = res.data;
      } else {
      }
    })
    .catch((err) => {
      console.log(err)
    })
  }

  // 리뷰 단일 조회
  const getReview = function (id) {
    axios.get(`${REST_API_URL}/${id}`)
      .then((response) => {
        review.value = response.data
      })
  }

  // 시설에 해당하는 리뷰만 조회
  const facilReviews = ref([])
  const getReviews = function(facilId) {
    axios.get(`${REST_API_URL}/${facilId}`)
    .then((response) => {
      facilReviews.value = response.data
    })
  }
  
  // 게시글 수정
  const updateReview = function () {
    axios.put(REST_API_URL, review.value) 
    .then(() => {
      router.push({ name: "reviews" })
    })
  }

  return { reviewList, review, getReviewList, getReview, updateReview, facilReviews, getReviews, registReview }
}, { persist: true})
