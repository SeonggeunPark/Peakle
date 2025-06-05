<template>
    <div class="container">
        <h4>사용자 {{ userStore.loginUser.name }}</h4>
        <hr>
        <h3>운동 통계</h3>
        <div class="record-container">
            <!-- 첫 번째 카드 -->
            <div class="record-card">
                <span class="record-title">총 운동 수 </span>
                <span class="record-value">{{ allRecordNum }}</span>
            </div>

            <!-- 두 번째 카드 -->
            <div class="record-card">
                <span class="record-title">총 완등 횟수 </span>
                <span class="record-value">{{ allProblemNum }}</span>
            </div>
        </div>
        <br>
        <div class="record-container">
            <!-- 첫 번째 카드 -->
            <div class="record-card">
                <span class="record-title">주간 운동 수 </span>
                <span class="record-value">{{ weekRecordNum }}</span>
            </div>

            <!-- 두 번째 카드 -->
            <div class="record-card">
                <span class="record-title">주간 완등 횟수 </span>
                <span class="record-value">{{ weekProblemNum }}</span>
            </div>
        </div>
        <hr>
        <h3>암장 방문 기록 <span style="font-size: 20px;">(Top 5)</span></h3>
        <TheBarChart v-if="facilityData.length > 0" :facilData="facilityData"/>
        <hr>
        <h3>주간 방문 횟수</h3>
        <TheLineChart v-if="weekVisitData.length>0" :userData="weekVisitData"/>
        <br>
        <h3>주간 완등 횟수</h3>
        <TheLineChart v-if="weekProData.length>0" :userData="weekProData"/>
    </div>
    <br>
</template>

<script setup>
import { useMyRecordStore } from '@/stores/myRecord';
import { useUserStore } from '@/stores/user';
import { onMounted, ref } from 'vue';
import TheBarChart from '../common/TheBarChart.vue';
import TheLineChart from '../common/TheLineChart.vue';

const userStore = useUserStore();
const recordStore = useMyRecordStore();
const allRecordNum = ref(0);
const weekRecordNum = ref(0);
const allProblemNum = ref(0);
const weekProblemNum = ref(0);
const facilityData = ref([{'없음':0}]);
const weekVisitData = ref([]);
const weekProData = ref([]);

onMounted(async () => {
    let user = null;
    if (userStore.loginUser) {
        user = userStore.loginUser;
    }
    try {
        // 총, 주간 방문 횟수
        const allData = await recordStore.getMyRecordDate(user.id,0);
        if (allData) {
            allRecordNum.value = allData.length;
            allProblemNum.value = getAllProblem(allData);
        }
        const weekData = await recordStore.getMyRecordDate(user.id,7);
        if (weekData) {
            weekRecordNum.value = weekData.length;
            weekProblemNum.value = getAllProblem(weekData);
        }

        // 시설별 방문 횟수
        if (allData) {
            facilityData.value = await allData.reduce((acc, item) => {
            const facilityId = item.facilityId;
            
            if (!acc[facilityId]) {
                    acc[facilityId] = 0; // 초기값 설정
                }
                
                acc[facilityId]++; // 갯수 증가
                
                return acc;
            }, {});
            facilityData.value = Object.entries(facilityData.value)
            .sort((a, b) => b[1] - a[1]) // 내림차순 정렬
            .slice(0, 5);
        }
        // 12주 전 날짜
        const twelveWeeksAgo = new Date();
        twelveWeeksAgo.setDate(twelveWeeksAgo.getDate() - 12 * 7);

        // 라인 차트 데이터 초기화
        weekVisitData.value = Array(12).fill(0);
        weekProData.value = Array(12).fill(0);
        // 데이터를 주 단위로 그룹화
        if (allData) {
            allData.forEach((item) => {
                const createdDate = new Date(item.created); // created를 Date 객체로 변환
    
                // 12주 이전 데이터는 무시
                if (createdDate < twelveWeeksAgo) return;
    
                // 오늘을 기준으로 몇 주 차인지 계산
                const diffInDays = Math.floor((new Date() - createdDate) / (1000 * 60 * 60 * 24)); // 날짜 차이 계산
                const weekIndex = Math.floor(diffInDays / 7); // 몇 번째 주인지 계산
    
                // 주가 12주 내라면 해당 주의 카운트 증가
                if (weekIndex >= 0 && weekIndex < 12) {
                    const tmp_item = parseRecord(item.record);
                    let num = 0;
                    tmp_item.forEach((item2) => {
                        num += item2.count
                    })
                    // 주간 운동 횟수
                    weekVisitData.value[11 - weekIndex]++; // 배열은 과거 데이터부터 저장되므로 뒤에서 앞으로 계산
                    // 주간 완등 횟수
                    weekProData.value[11 - weekIndex] += num;
                }
            });
        }
    } catch (err) {
        console.log(err)
    }
})

const parseRecord = (record) => {
  if (!record) return [];
  return record.split(', ').map(item => {
    const [color, count] = item.split(':');
    return { color, count: parseInt(count, 10) };
  });
};

const getAllProblem = function (data) {
    if (data.length==0) {
        return 0;
    }
    let num = 0;
    for (let index = 0; index < data.length; index++) {
        const record = data[index].record.split(',');
        for (let i2 = 0; i2 < record.length; i2++) {
            const value = record[i2].split(':')[1].trim(); // 숫자 추출
            num += parseInt(value, 10); // 숫자로 변환하여 합산
        }
    }
    return num;
}

</script>

<style scoped>
h3, h4 {
    text-align: left;
}

.record-container {
    display: flex;
    /* 카드들을 가로로 배치 */
    gap: 16px;
    /* 카드 간격 */
    justify-content: center;
    /* 중앙 정렬 */
    align-items: center;
    /* 세로 중앙 정렬 */
}

.record-card {
    display: flex;
    flex-direction: row;
    /* 카드 내부 요소를 세로로 정렬 */
    justify-content: space-between;
    /* 내부 텍스트 중앙 정렬 */
    align-items: center;
    /* 내부 텍스트 중앙 정렬 */
    border: 1px solid #ccc;
    /* 테두리 */
    border-radius: 8px;
    /* 둥근 모서리 */
    padding: 16px;
    /* 내부 여백 */
    width: 500px;
    /* 고정된 카드 너비 */
    height: 40px;
    /* 카드 높이 조정 */
    text-align: center;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    /* 그림자 효과 */
    background-color: #fff;
    /* 흰색 배경 */
}

.record-title {
    font-size: 14px;
    /* 제목 크기 */
    color: #555;
    /* 제목 색상 */
}

.record-value {
    font-size: 20px;
    /* 값의 크기 */
    font-weight: bold;
    /* 굵은 글씨 */
    color: #333;
    /* 값의 색상 */
}
</style>