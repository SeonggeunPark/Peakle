<script>
import { Line } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  LineElement,
  CategoryScale,
  LinearScale,
  PointElement,
} from "chart.js";

ChartJS.register(Title, Tooltip, Legend, LineElement, CategoryScale, LinearScale, PointElement);

export default {
  name: "LineChart",
  props: {
    userData: {
      type: Array,
      required: true,
      validator: (value) => value.length === 12, // 데이터 길이는 12로 고정
    },
  },
  components: { Line },
  data() {
    return {
      chartData: {
        labels: this.generateLabels(), // 주차 레이블 생성
        datasets: [
          {
            data: this.userData, // Prop으로 받은 데이터
            borderColor: "#36A2EB",
            backgroundColor: "rgba(54, 162, 235, 0.2)",
            pointBackgroundColor: "#36A2EB",
            tension: 0.4,
          },
        ],
      },
      chartOptions: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          y: {
            beginAtZero: true,
            grid: {
              display: true,
            },
          },
          x: {
            grid: {
              display: false,
            },
          },
        },
        plugins: {
          legend: {
            display: false,
            position: "top",
          },
        },
      },
    };
  },
  methods: {
    generateLabels() {
      const labels = [];
      const today = new Date();

      for (let i = 0; i < 12; i++) {
        const weekDate = new Date(today);
        weekDate.setDate(today.getDate() - i * 7);

        const month = weekDate.toLocaleString("default", { month: "long" }); // 월 이름 가져오기
        const week = Math.ceil(weekDate.getDate() / 7); // 주차 계산
        labels.unshift(`${month} ${week}주차`);
      }

      return labels;
    },
  },
};
</script>

<template>
  <div>
    <Line :data="chartData" :options="chartOptions" />
  </div>
</template>

<style scoped>
.chart-container {
  width: 100%;
  height: 400px;
}
</style>
