<template>
  <div v-if="chartData.labels.length > 0">
    <Bar style="width: 100%; height: 250px;" id="my-chart-id" :options="chartOptions" :data="chartData" />
  </div>
</template>

<script>
import { Bar } from 'vue-chartjs'
import { Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale } from 'chart.js'
import { useFacilityStore } from '@/stores/facility';

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default {
  name: 'BarChart',
  props: {
    facilData: {
      type: Array, // 데이터 형식 (배열)
      required: true, // 필수 여부
    },
  },
  components: { Bar },
  data() {
    return {
      chartData: {
        labels: [],
        datasets: []
      },
      chartOptions: {
        responsive: true,
        indexAxis: 'y',
        scales: {
          x: {
            ticks: {
              stepSize: 1,
            }
          }
        },
        plugins: {
          legend: {
            display: false, // 범례 숨기기
          },
        },
        barThickness: 25,
        // backgroundColor: 'rgba(113,201,206,2)',
        // borderColor: 'rgba(17,45,78,1)',
        backgroundColor: '#71C9CE',
        borderRadius: 6,
        borderWidth: 1,
        categoryPercentage: 0.8, // 막대 카테고리 간격 비율 (0 ~ 1)
        barPercentage: 0.9, // 막대 폭 비율 (0 ~ 1)
      },
    }
  },
  watch: {
    facilData: {
      immediate: true, // 컴포넌트 초기화 시에도 실행
      handler(newValue) {
        if (newValue && newValue.length) {
          this.updateChartData();
        }
      },
    },
  },
  methods: {
    async updateChartData() {
      try {
        const facilStore = useFacilityStore();
        await facilStore.getFacilNames(); // 필요 시 시설 이름 로드
        this.chartData = {
          labels: this.facilData.map((item) => facilStore.facilNames[item[0]]), // 시설 이름 매칭
          datasets: [{ data: this.facilData.map((item) => item[1]) }],
        };
        this.chartOptions.scales.x.max = Math.ceil(Math.max(...this.facilData.map((item) => item[1])) / 10) * 10;
      } catch (error) {
        console.error("Error updating chart data:", error);
      }
    },
  },
}
</script>

<style scoped>

</style>