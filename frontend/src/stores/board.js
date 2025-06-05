import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import axios from 'axios'
import router from '@/router'
import { useUserStore } from './user'

const REST_API_URL = `http://localhost:8080/board`

export const useBoardStore = defineStore('board', () => {
  const boardList = ref([]) //게시글 목록을 스토어에서 관리하겠다~
  const reviewList = ref([])
  const board = ref({})
  const review = ref({})
  const newReviewContent = ref("");

  const getBoardList = function () {
    axios.get(REST_API_URL, {
      headers: {
        'access-token': sessionStorage.getItem('access-token')
      }
    })
      .then((response) => {
        boardList.value = response.data
      })
  }


  //게시글 등록
  const createBoard = function (board) {
    axios({
      url: REST_API_URL,
      method: 'POST',
      data: board
    })
      .then(() => {
        router.push({ name: 'boardList' })
      })
      .catch(() => {
        console.log("실패")
      })
  }

  const getBoard = function (id) {
    axios.get(`${REST_API_URL}/${id}`)
      .then((response) => {
        board.value = response.data
      })
  }

  const updateBoard = function () {
    axios.put(REST_API_URL, board.value)
      .then(() => {
        router.push({ name: 'boardList' })
      })
  }

  const searchBoardList = function (searchCondition) {
    axios.get(REST_API_URL, {
      params: searchCondition
    })
      .then((res) => {
        boardList.value = res.data
      })
  }

  const popularBoard = ref([]);
  const getPopularBoard = function (searchCondition) {
    axios.get(REST_API_URL, {
      params: searchCondition
    })
    .then((res) => {
      popularBoard.value = res.data
    })
  }
  
  const recentBoard = ref([]);
  const getRecentBoard = function (searchCondition) {
    axios.get(REST_API_URL, {
      params: searchCondition
    })
      .then((res) => {
        recentBoard.value = res.data
      })
  }


  // 댓글 불러오기
  const getReviewList = function(boardId) {
    axios.get(`${REST_API_URL}review/${boardId}/0`)
    .then((res) => {
      console.log(res.data)
      if (res.status == 200) {
        reviewList.value = res.data;
      }
    })
    .catch((err) => {
      console.log("데이터 없음");
    })
  }

    // 댓글 추가
    const createReview = async (boardId, content) => {
      const userStore = useUserStore();
      const userId = userStore.loginUser?.id || '';
      const writer = userStore.loginUser?.name || '';
      
      try {
        await axios.post(`${REST_API_URL}review`, { boardId, userId, content, writer });
        await getReviewList(boardId); // 댓글 목록 갱신
      } catch (error) {
        console.error('댓글 등록 실패:', error);
      }
    };
  
    // 댓글 삭제
    const deleteReview = async (reviewId, boardId) => {
      try {
        await axios.delete(`${REST_API_URL}review/${reviewId}`);
        await getReviewList(boardId); // 댓글 목록 갱신
      } catch (error) {
        console.error('댓글 삭제 실패:', error);
      }
    };
  
    // 댓글 수정
    const updateReview = async (reviewId, content, boardId) => {
      try {
        await axios.put(`${REST_API_URL}review`, {
          content: content,
          id: reviewId,
        });
        await getReviewList(boardId); // 댓글 목록 갱신
      } catch (error) {
        console.error('댓글 수정 실패:', error);
      }
    };

  return { boardList, getBoardList, createBoard, board, getBoard, updateBoard, searchBoardList,
    getReviewList, createReview, deleteReview, updateReview, newReviewContent, review, reviewList,
    getPopularBoard, popularBoard, getRecentBoard, recentBoard
   }
})
