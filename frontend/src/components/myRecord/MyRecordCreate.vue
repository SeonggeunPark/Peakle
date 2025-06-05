<template>
    <div class="container">
        <h4 style="font-family: buttonTitle;">게시글 작성</h4>
        <div class="form-floating mb-3">
            <input type="text" class="form-control" id="title" placeholder="제목" v-model="record.title">
            <label for="title">제목</label>
        </div>

        <!-- 암장 선택 버튼 -->
        <div class="form-floating mb-3 d-flex align-items-center">
            <button class="btn custom-btn me-3" @click="openModal">암장 선택</button>
            <div v-if="showModal" class="modal-backdrop">
                <div class="modal-content">
                    <h3 style="font-family: buttonTitle;">암장 선택</h3>
                    <!-- 고정된 검색창 -->
                    <div class="sticky-header">
                        <input v-model="searchKeyword" placeholder="암장 이름을 검색하세요" @input="fetchGyms" class="search-box" />
                    </div>
                    <!-- 스크롤 가능한 리스트 -->
                    <ul class="gym-list">
                        <li v-for="(gym, index) in filteredGyms" 
                            :key="gym.id" 
                            class="list-group-item d-flex align-items-center p-3 mb-3 shadow-sm"
                            @click="selectGym(gym);">
                            <img :src="`/img/facilLogo/${gym.id}.png` || '/img/loading/loading.png'" 
                                class="facility-icon me-4 rounded"
                                style="width: 50px; height: 50px; object-fit: cover;" />
                            <div>
                                <h6 class="mb-0">{{ gym.name }}</h6>
                            </div>
                        </li>
                    </ul>
                    <button @click="closeModal" class="btn custom-btn">닫기</button>
                </div>
            </div>
            <div v-if="record.facility" class="selected-facility">
                <div class="facility-item d-flex align-items-center">
                    <img :src="`/img/facilLogo/${record.facilityId}.png` || '/img/loading/loading.png'" 
                                class="facility-icon me-4 rounded"
                                style="width: 50px; height: 50px; object-fit: cover;" />
                    <span class="facility-name ms-2">{{ record.facility }}</span>
                </div>
            </div>
        </div>

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
        <!-- 내용 입력 -->
        <div class="form-floating mb-3">
            <textarea class="form-control" id="content" placeholder="내용" style="height: 200px"
                v-model="record.content"></textarea>
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
                        :class="{ active: star <= record.rating }"
                        @click="setRating(star)"
                    >
                        ★
                    </span>
                </div>
            </div>
        </div>

        <!-- 등록 버튼 -->
        <div class="d-flex justify-content-end">
            <button class="btn custom-btn" @click="createRecord">등록</button>
        </div>
        <br>
    </div>
</template>

<script>
import { ref, watch } from "vue";
import { useMyRecordStore } from "@/stores/myRecord";
import axios from "axios";
import { useUserStore } from "@/stores/user";

export default {
    setup() {
        const showModal = ref(false); // 암장 선택 모달 표시 여부
        const searchKeyword = ref(""); // 검색 키워드
        const gymList = ref([]); // 서버에서 가져온 짐 목록
        const filteredGyms = ref([]); // 검색어로 필터링된 짐 목록

        const showColorModal = ref(false); // 색상 선택 모달 표시 여부
        const selectedColor = ref(null); // 선택된 색상
        const selectedCount = ref(1); // 선택된 개수
        const selectedColors = ref([]); // 선택된 색상 및 개수 목록

        // color 폴더에 있는 이미지 목록
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

        const store = useMyRecordStore();

        const record = ref({
            title: "",
            content: "",
            record: "",
            rating: "",
            facility: "",
            facilityId: "",
        });

        // 게시글 등록
        const createRecord = function () {
            const userStore = useUserStore();
            record.value.userId = userStore.loginUser?.id;
            record.value.writer = userStore.loginUser?.name;
            record.value.record = selectedColors.value
                .map((color) => `${color.color}:${color.count}`)
                .join(", ");
            if (record.value.facilityId=="" || record.value.rating=="" || record.value.title==""
                || record.value.record=="") {
                alert("입력 사항을 확인해 주십시오.")
            } else {
                store.CreateMyRecord(record.value);
            }
        };

        // 암장 선택 모달 열기
        const openModal = () => {
            showModal.value = true;
            fetchGyms(); // 모달을 열 때 초기 목록 가져오기
        };

        // 암장 선택 모달 닫기
        const closeModal = () => {
            showModal.value = false;
        };

        // 검색어로 짐 목록 가져오기
        const fetchGyms = async () => {
            try {
                const response = await axios.post("http://localhost:8080/facility/search", { keyword: searchKeyword.value });
                gymList.value = response.data;
                filteredGyms.value = gymList.value; // 처음엔 모든 결과를 표시
            } catch (error) {
                console.error("짐 목록 가져오기 실패:", error);
            }
        };

        // 짐 선택 처리
        const selectGym = (gym) => {
            record.value.facility = gym.name;
            record.value.facilityId = gym.id; // 선택된 짐 저장
            closeModal(); // 모달 닫기
        };

        // 색상 선택 모달 열기
        const openColorModal = () => {
            showColorModal.value = true;
        };

        // 색상 선택 모달 닫기
        const closeColorModal = () => {
            showColorModal.value = false;
        };

        // 색상 이미지 선택
        const selectColor = (image) => {
            selectedColor.value = image;
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

        // 개수 더하기
        const incrementCount = (index) => {
        selectedColors.value[index].count++;
        }
        
        // 개수 빼기
        const decrementCount = (index) => {
            if (selectedColors.value[index].count > 1) {
                selectedColors.value[index].count--;
            }
        }

        // 색상 제거
        const removeColor = (index) => {
            selectedColors.value.splice(index, 1);
        };

        // 검색어 변경 시 필터링
        watch(searchKeyword, (newKeyword) => {
            filteredGyms.value = gymList.value.filter((gym) =>
                gym.name.toLowerCase().includes(newKeyword.toLowerCase())
            );
        });

        // 별점 설정 함수
        const setRating = (rating) => {
            record.value.rating = rating;
        };

        return {
            record,
            showModal,
            searchKeyword,
            gymList,
            filteredGyms,
            showColorModal,
            colorImages,
            selectedColor,
            selectedCount,
            selectedColors,
            createRecord,
            openModal,
            closeModal,
            openColorModal,
            closeColorModal,
            selectColor,
            addColorCount,
            selectGym,
            removeColor,
            setRating,
            incrementCount,
            decrementCount,
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