<template>
  <div>
    <div v-for="review in reviewList" :key="review.id" class="review-item">
      <div class="review-header d-flex">
            <div class="profile me-2">
                <img :src="`/img/character/${review.userId%11}.png`" alt="">
            </div>
            <div>
                <p class="review-author my-0">
                    {{ review.writer }}
                </p>
                <p class="review-timestamp my-0">
                    {{ review.created }}
                </p>
            </div>
      </div>
      <div class="review-content">
        <p>{{ review.content }}</p>
      </div>
      <div v-if="isOner(review.userId)" class="review-actions">
        <button @click="handleEditReview(review.id)">수정</button>
        <button @click="handleDeleteReview(review.id)">삭제</button>
      </div>
    </div>
    <div class="review-form">
      <textarea v-model="newReviewContent" placeholder="댓글을 입력하세요"></textarea>
      <button @click="handleCreateReview">댓글 등록</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useBoardStore } from "@/stores/board";
import { useUserStore } from "@/stores/user";

// Props 정의
const props = defineProps({
  boardId: {
    type: Number,
    required: true,
  },
});

// Store 가져오기
const store = useBoardStore();
const userStore = useUserStore();

// State와 Method 연결
const reviewList = computed(() => store.reviewList);
const newReviewContent = ref("");

// 수정삭제 권한
const isOner = function(userId) {
  if (userStore.loginUser?.id == userId) {
    return true;
  }
  return false;
}

// 댓글 가져오기
const fetchReviews = () => {
  store.getReviewList(props.boardId);
};

// 댓글 등록
const handleCreateReview = () => {
  if (!newReviewContent.value.trim()) {
    alert("댓글 내용을 입력하세요.");
    return;
  }
  store.createReview(props.boardId, newReviewContent.value).then(() => {
    newReviewContent.value = ""; // 입력 필드 초기화
  });
};

// 댓글 삭제
const handleDeleteReview = (reviewId) => {
  if (confirm("정말로 삭제하시겠습니까?")) {
    store.deleteReview(reviewId, props.boardId);
  }
};

// 댓글 수정
const handleEditReview = (reviewId) => {
  const updatedContent = prompt("수정할 내용을 입력하세요:");
  if (updatedContent) {
    store.updateReview(reviewId, updatedContent, props.boardId);
  }
};

// 컴포넌트 마운트 시 댓글 가져오기
onMounted(() => {
  fetchReviews();
});
</script>

<style scoped>
/* 전체 댓글 목록 컨테이너 */
.review-item {
  display: flex;
  flex-direction: column;
  border-bottom: 1px solid #ddd;
  padding: 10px 0;
  margin-bottom: 10px;
}

/* 댓글 헤더 */
.review-header {
  /* display: flex; */
  /* justify-content: space-between; */
  align-items: center;
  font-size: 14px;
  color: #666;
  margin-bottom: 5px;
}

.profile {
  width: 40px;
    height: 40px;
    border-radius: 100%;
    /* border-style: solid; */
    /* border-width: 1px; */
    /* border-color: gra; */
    /* box-shadow: 1px 1px 1px 1px gray; */
    overflow: hidden;
    background-color: #e4e9e9;
}
img {
    width: 100%;
    height: 100%;
}
.review-timestamp {
  font-size: 11px;
  color: #999;
}

/* 댓글 내용 */
.review-content {
  font-size: 15px;
  line-height: 1.5;
  margin-bottom: 10px;
  color: #333;
}

/* 수정/삭제 버튼 컨테이너 */
.review-actions {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

.review-actions button {
  padding: 5px 10px;
  font-size: 12px;
  color: #007bff;
  background-color: transparent;
  border: 1px solid #007bff;
  border-radius: 3px;
  cursor: pointer;
}

.review-actions button:hover {
  color: #fff;
  background-color: #007bff;
}

/* 댓글 작성 폼 */
.review-form {
  margin-top: 20px;
  padding-top: 10px;
  border-top: 1px solid #ddd;
}

.review-form textarea {
  width: 100%;
  height: 80px;
  padding: 10px;
  font-size: 14px;
  border: 1px solid #ddd;
  border-radius: 5px;
  resize: none;
}

.review-form textarea:focus {
  outline: none;
  border-color: #007bff;
  box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
}

.review-form button {
  margin-top: 10px;
  padding: 8px 15px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 3px;
  cursor: pointer;
}

.review-form button:hover {
  background-color: #0056b3;
}
</style>


