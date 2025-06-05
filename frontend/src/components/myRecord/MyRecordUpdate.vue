<template>
    <div class="container">
        <h4>기록 수정</h4>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="title" placeholder="제목" v-model="store.record.title">
            <label for="title">제목</label>
        </div>
        <div class="facility-item d-flex align-items-center">
            <img :src="`/img/facilLogo/${store.record.facilityId}.png` || '/img/loading/loading.png'" 
                        class="facility-icon me-4 rounded"
                        style="width: 50px; height: 50px; object-fit: cover;" />
            <span class="facility-name ms-2">{{ store.record.facilityName }}</span>
        </div>
        <br>
        <div class="selected-colors">
            <!-- 색상 및 개수 선택 -->
            <div class="form-floating mb-3 d-flex">
                <button class="btn custom-btn" @click="openColorModal">기록 입력</button>
                <div v-if="showColorModal" class="modal-backdrop">
                    <div class="modal-content">
                        <h3>난이도 별 완등 문제</h3>

                        <!-- 색상 이미지 표시 -->
                        <div class="color-selector">
                            <div v-for="(image, index) in colorImages" :key="index" class="color-image">
                                <img :src="image.src" :alt="image.alt" class="color-image-thumbnail"
                                    @click="selectColor(image)" :class="{ selected: selectedColor === image }" />
                            </div>
                        </div>

                        <div v-if="selectedColor">
                            <!-- 선택된 색상에 개수 입력 -->
                            <div class="input-group mb-3">
                                <input v-model="selectedCount" type="number" min="1" max="10" placeholder="개수를 입력하세요"
                                    class="form-control" />
                                <button @click="addColorCount" class="btn custom-btn">추가</button>
                            </div>
                        </div>

                        <button @click="closeColorModal" class="btn custom-btn">닫기</button>
                    </div>
                </div>
            </div>

            <!-- 선택된 색상 목록 -->
            <div v-if="selectedColors.length > 0">
                <div class="color-list d-flex align-items-center">
                    <div
                        v-for="(colorItem, index) in selectedColors"
                        :key="index"
                        class="color-item d-flex align-items-center me-4"
                    >
                        <!-- 색상 미리보기 -->
                        <div
                            class="color-preview custom-record-btn"
                            :style="{ backgroundColor: colorItem.color
                            }"
                        ></div>
                        <!-- 개수 및 버튼 -->
                        <div class="ms-2 d-flex align-items-center">
                            <button
                                class="btn custom-record-btn btn-sm custom-btn"
                                style="border: 0px;"
                                @click="decrementCount(index)"
                            >
                                -
                            </button>
                            <span class="mx-2">{{ colorItem.count }}개</span>
                            <button
                                class="btn custom-record-btn btn-sm custom-btn"
                                style="border: 0px;"
                                @click="incrementCount(index)"
                            >
                                +
                            </button>
                        </div>
                        <!-- 삭제 버튼 -->
                        <button
                            class="btn btn-danger btn-sm ms-3"
                            @click="removeColor(index)"
                        >
                            삭제
                        </button>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-floating mb-3">
            <textarea class="form-control" id="content" placeholder="내용" style="height: 200px"
                v-model="store.record.content"></textarea>
            <label for="content">내용</label>
        </div>

        <!-- 별점 입력 -->
        <div class="form-floating mb-3">
            <label class="rating-label">별점</label>
            <div class="rating-container">
                <div class="rating-stars">
                <span
                    v-for="star in 5"
                    :key="star"
                    class="star"
                    :class="{ active: star <= store.record.rating }"
                    @click="setRating(star)"
                >
                    ★
                </span>
                </div>
            </div>
        </div>
        <div class="d-flex justify-content-end">
            <button class="btn custom-btn mx-3" @click="updateRecord">수정</button>
            <button class="btn custom-btn" @click="backButton">뒤로</button>
        </div>
        <br>
    </div>
</template>

<script>
import { useMyRecordStore } from '@/stores/myRecord';
import { useRouter } from 'vue-router';
import { ref, onMounted } from "vue";

export default {
  setup() {
    const store = useMyRecordStore();
    const router = useRouter();
    const showColorModal = ref(false);
    const selectedColors = ref([]); // 선택된 색상 및 개수
    const selectedColor = ref(null); // 현재 선택된 색상
    const selectedCount = ref(1); // 현재 입력된 개수
    const colorImages = ref([
        { src: "/img/colors/Red.png", alt: "빨간색", color: "red" },
        { src: "/img/colors/Orange.png", alt: "주황색", color: "orange" },
        { src: "/img/colors/Yellow.png", alt: "노란색", color: "yellow" },
        { src: "/img/colors/Green.png", alt: "초록색", color: "green" },
        { src: "/img/colors/Blue.png", alt: "파란색", color: "blue" },
        { src: "/img/colors/Indigo.png", alt: "남색", color: "navy" },
        { src: "/img/colors/Purple.png", alt: "보라색", color: "purple" },
        { src: "/img/colors/White.png", alt: "흰색", color: "white" },
        { src: "/img/colors/Black.png", alt: "검은색", color: "black" },
        { src: "/img/colors/Pink.png", alt: "핑크색", color: "pink" },
    ]);

    onMounted(async () => {
        await store.getMyRecord(store.record.id);
        store.record.record = parseRecord(store.record.record);
        selectedColors.value = store.record.record;
    })

    const parseRecord = (record) => {
        if (!record) return [];
        return record.split(', ').map(item => {
            const [color, count] = item.split(':');
            return { color, count: parseInt(count, 10) };
        });
    };

    // 색상 및 개수 추가
    const addColorCount = () => {
        if (selectedColors.value.length < 3) {
            if (selectedColor.value && selectedCount.value > 0) {
                const colorExists = selectedColors.value.find(
                    (item) => item.color === selectedColor.value.color
                );
                if (colorExists) {
                    colorExists.count += selectedCount.value;
                } else {
                    selectedColors.value.push({
                        color: selectedColor.value.color,
                        count: selectedCount.value,
                        src: selectedColor.value.src, // 이미지 경로 추가
                    });
                }
            }
        } else {
            alert("최대 3개의 색상만 선택할 수 있습니다.");
        }
        selectedCount.value = 1; // 개수 초기화
    };

    // 색상 이미지 선택
    const selectColor = (image) => {
        selectedColor.value = image;
    };

    // 색상 개수 증가
    const incrementCount = (index) => {
      selectedColors.value[index].count++;
    };

    // 색상 개수 감소
    const decrementCount = (index) => {
      if (selectedColors.value[index].count > 1) {
        selectedColors.value[index].count--;
      }
    };

    // 색상 삭제
    const removeColor = (index) => {
      selectedColors.value.splice(index, 1);
    };

    // 모달 열기/닫기
    const openColorModal = () => {
      showColorModal.value = true;
    };

    const closeColorModal = () => {
      showColorModal.value = false;
    };

    // 별점 설정 함수
    const setRating = (rating) => {
        store.record.rating = rating;
    };

    // 게시글 수정 및 변환
    const updateRecord = function () {
        store.record.record = store.record.record
                .map((color) => `${color.color}:${color.count}`)
                .join(", ");
        store.updateMyRecord();
    }
    const backButton = function () {
        router.go(-1);
    }

    

    return {
      store,
      showColorModal,
      selectedColors,
      selectedColor,
      selectedCount,
      selectColor,
      colorImages,
      openColorModal,
      closeColorModal,
      addColorCount,
      incrementCount,
      decrementCount,
      removeColor,
      updateRecord,
      backButton,
      setRating,
    };
  },
};

</script>

<style scoped>

/* 암장 버튼*/
.custom-btn {
  font-family: 'buttonTitle';
  color: black;
  background-color: #A6E3E9;
}

.custom-btn:hover {
  background-color: #71C9CE;  
}

/* 기록 입력 리스트 */
.custom-record-btn { 
    width: 30px; 
    height: 30px; 
    border-radius: 50%; 
    border: 2px solid #000;
}

/* modal css */
.modal-backdrop {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
}

.modal-content {
    background: white;
    padding: 20px;
    border-radius: 8px;
    width: 400px;
    max-height: 600px;
    display: flex;
    flex-direction: column;
    overflow: hidden; /* 리스트와 검색창을 분리 */
}

.sticky-header {
    position: sticky;
    top: 0;
    background: white;
    z-index: 10;
    padding-bottom: 10px;
    border-bottom: 1px solid #ddd; /* 구분선 추가 */
}

.gym-list {
    overflow-y: auto; /* 리스트 스크롤 가능 */
    flex-grow: 1; /* 남은 공간을 채움 */
}

.search-box {
    width: 100%;
    padding: 10px;
    border: 1px solid #ddd;
    border-radius: 4px;
}

/* 색상 선택 이미지 */
.color-selector {
  display: flex;
  flex-wrap: wrap;
}

.color-image {
  margin: 10px;
}

.color-image-thumbnail {
  width: 50px;
  height: 50px;
  border-radius: 4px;
  cursor: pointer;
}

.color-image-thumbnail.selected {
  border: 2px solid #000;
}

.selected-colors {
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    gap: 10px;
    align-items: center;
}

.color-list {
    margin: 1rem;
    display: flex;
    flex-wrap: nowrap;
}

.color-item {
    display: flex;
    align-items: center;
}

.color-preview {
    display: inline-block;
}

.input-group {
  margin-top: 10px;
}

.selected-facility {
    border: 1px solid #ddd;
    border-radius: 10px;
    padding: 10px;
    margin-top: 10px;
    background-color: #f9f9f9; /* 배경 색상 */
}

.facility-item {
    display: flex;
    align-items: center;
}

.facility-icon {
    margin-right: 8px;
}

.facility-name {
    font-size: 1rem;
    font-weight: bold;
}

.rating-label {
    font-size: 1rem;
    margin-bottom: 0.5rem;
    display: block; /* 제목처럼 위에 위치 */
    color: #6c757d; /* 기본 Bootstrap 색상 */
}

.rating-container {
    display: flex;
    justify-content: flex-end; /* 별을 오른쪽에 정렬 */
    align-items: center;
}

.rating-stars {
    display: flex;
    gap: 5px; /* 별 사이 간격 */
    cursor: pointer;
}

.star {
    font-size: 2rem;
    color: gray;
    transition: color 0.2s;
}

.star.active {
    color: yellow; /* 선택된 별은 노란색 */
}

</style>