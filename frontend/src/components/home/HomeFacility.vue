<template>
    <div>
        <div class="text-start">
            <h4>주간 인기 클라이밍장 TOP 5 💪</h4>
        </div>
        <div class="top-user">
            <TheBarChartFacil v-if="facilityData.length > 0" :facilData="facilityData" />
        </div>
    </div>
</template>

<script setup>
import { useMyRecordStore } from '@/stores/myRecord';
import { onMounted, ref } from 'vue';
import TheBarChart from '../common/TheBarChart.vue';
import TheBarChartFacil from '../common/TheBarChartFacil.vue';

const recordStore = useMyRecordStore();
const facilityData = ref([]);

onMounted(async () => {
    try {
        // 총, 주간 방문 횟수
        const weekData = await recordStore.getMyRecordDate(0, 7);
        // 시설별 방문 횟수
        if (weekData && weekData.length > 0) {
            const facilityVisitCounts = await weekData.reduce((acc, item) => {
                const facilityId = item.facilityId;

                if (!acc[facilityId]) {
                    acc[facilityId] = 0; // 초기값 설정
                }

                acc[facilityId]++; // 갯수 증가

                return acc;
            }, {});
            facilityData.value = Object.entries(facilityVisitCounts)
                .sort((a, b) => b[1] - a[1]) // 내림차순 정렬
                .slice(0, 5);
        }
    } catch (err) {
        console.log(err)
    }
})
</script>

<style scoped></style>