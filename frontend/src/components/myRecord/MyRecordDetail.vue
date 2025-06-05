<template>
    <div class="container">
        <h4>기록 상세</h4>
        <hr />
        
        <div class="d-flex justify-content-center">
            <div class="card" style="width: 30rem">
                <div class="card-body">
                    <div class="mb-3 d-flex justify-content-between align-items-center">
                        <h4 class="card-title">{{ store.record.title }}</h4>
                        <div class="d-flex justify-content-end">
                            <!-- <h6 class="card-subtitle mx-3 text-body-secondary">{{ store.record.writer }}</h6> -->
                            <h6 class="card-subtitle text-body-secondary">{{ store.record.created }}</h6>
                        </div>
                    </div>
                    <div class="facility-item d-flex align-items-center">
                        <img :src="`/img/facilLogo/${store.record.facilityId}.png` || '/img/loading/loading.png'" 
                                    class="facility-icon me-4 rounded"
                                    style="width: 50px; height: 50px; object-fit: cover;" />
                        <span class="facility-name ms-2">{{ store.record.facilityName }}</span>
                    </div>
                    <br>
                    <div v-if="store.record.record">
                        <div class="color-list d-flex align-items-center">
                            <div
                                v-for="(colorItem, index) in store.record.record"
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
                                    <span class="mx-2">{{ colorItem.count }}개</span>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br>
                    <p>
                        {{ store.record.content }}
                    </p>
                    <div class="form-floating mb-3">
                        <div class="rating-container">
                            <div class="rating-stars">
                            <span
                                v-for="star in 5"
                                :key="star"
                                class="star"
                                :style="{ color: star <= store.record.rating ? 'gold' : 'gray' }"
                            >
                                ★
                            </span>
                            </div>
                        </div>
                    </div>
                    <div class="d-flex justify-content-center">
                        <button class="mx-3 btn custom-btn" @click="moveUpdate">수정</button>
                        <button class="mx-3 btn custom-btn" @click="deleteRecord">삭제</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <br>
</template>

<script setup>
import { useMyRecordStore } from '@/stores/myRecord';
import axios from 'axios';
import { onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';


const route = useRoute();
const router = useRouter();
const store = useMyRecordStore();

onMounted(async () => {
    await store.getMyRecord(route.params.id);
    store.record.record = parseRecord(store.record.record);
})

const moveUpdate = function () {
    router.push({name:'myRecordUpdate'});
};

const parseRecord = (record) => {
  if (!record) return [];
  return record.split(', ').map(item => {
    const [color, count] = item.split(':');
    return { color, count: parseInt(count, 10) };
  });
};


const deleteRecord = function () {
    const check = window.confirm("정말 삭제하시겠습니까?");
    if (check) {
        axios
            .delete(`http://localhost:8080/myrecord/${route.params.id}`)
            .then(() => {
                alert("기록이 삭제되었습니다.")
                router.replace({ name: "calender" });
            })
            .catch(() => { });
    }
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

p {
    text-align: left;
}

/* 별 생성 */
.star {
  font-size: 24px; /* 별 크기 */
  cursor: pointer; /* 클릭 가능한 포인터 표시 */
  transition: color 0.3s; /* 색상 전환 효과 */
}

.rating-stars {
  display: flex; /* 별들을 가로로 배치 */
}

</style>