import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'

const REST_API_URL = 'http://localhost:8080'

export const useLikeStore = defineStore('like', () => {
  const likeList = ref([])
  const like = ref()

  const addLike = function(userId, facilId) {
    axios.post(`${REST_API_URL}/user/facility`, {
      userId: userId,
      facilityId: facilId
    })
    .then((res) => {
    })
    .catch((err) => {
      console.error(err);
    });
  }

  const removeLike = function(userId, facilId) {
    axios.delete(`${REST_API_URL}/user/facility`, {
      data: {
        userId: userId,
        facilityId: facilId
      }
    })
    .then((res) => {
    })
  }

  const getLikes = function(userId) {
    return axios.get(`${REST_API_URL}/user/facility/${userId}`)
    .then((res) => {
      if (res.data) {
        likeList.value = res.data;
      }
    })
    .catch((err) => {
      console.error(err);
    });
  }

  return { likeList, addLike, removeLike, getLikes }
})
