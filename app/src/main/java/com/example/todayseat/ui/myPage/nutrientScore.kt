/*`import android.R
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.todayseat.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Utils
import java.util.*


class nutrientScore : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName

    private val chartData = ArrayList<Entry>() // 데이터 배열
    private var lineDataSet = ArrayList<ILineDataSet>()
    private  var lineData : LineData = LineData()
    lateinit var chart : LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.todayseat.R.layout.activity_main)
    }

    //차트 데이터 초기화 메소드
    private fun initChartData(){

        chartData.add(Entry(-240f,0f))

        var set = LineDataSet(chartData,"set1")
        lineDataSet.add(set)
        lineData = LineData(lineDataSet)

        set.lineWidth = 2F // 차트의 선의 굵기
        set.setDrawValues(false)
        set.highLightColor = Color.TRANSPARENT // 차트의 점을 클릭했을 때 나오는 + 모양의 선
        set.mode = LineDataSet.Mode.STEPPED // 해당 데이터 셋을 나타내는 차트의 모드
    }

    private fun initChart(){
        chart.run {
            setDrawGridBackground(false)
            setBackgroundColor(Color.WHITE)
            legend.isEnabled = false
        }

        val xAxis = chart.xAxis
        xAxis.setDrawLabels(true)
        xAxis.axisMaximum = 1200f
        xAxis.axisMinimum = -240f
        xAxis.labelCount = 5
        xAxis.valueFormatter = TimeAxisValueFormat()

        xAxis.textColor = Color.BLACK
        xAxis.position = XAxis.XAxisPosition.BOTTOM // X축라벨
        xAxis.setDrawLabels(true) //Grid-line
        xAxis.setDrawAxisLine(true) //Axis-line

        //왼쪽 y축 값
        val yLAxis = chart.axisLeft
        yLAxis.axisMaximum=4.5f
        yLAxis.axisMinimum=-0.5f

        //왼쪽 y축 도메인 변경
        val yAxisvals = kotlin.collections.ArrayList<String>(Arrays.asList("F","C","B","A","A+"))
        yLAxis.valueFormatter = IndexAxisValueFormatter(yAxisvals)
        yLAxis.granularity = 1f

        //오른쪽 y축 값
        val yRaxis = chart.axisRight
        yRaxis.setDrawLabels(false)
        yRaxis.setDrawAxisLine(false)
        yRaxis.setDrawGridLines(false)

        //마커 설정
        val marker = LineMarkerView(requireContext(), com.google.android.material.R.id.layout.graph_marker)
        marker.chartView = chart
        chart.marker = marker

        chart!!.description.isEnabled = false
        chart!!.data = lineData

        chart!!.invalidate() // 다시그리기




    }


    private fun LineChart(chartData: ArrayList<Entry>) {
        lineChart = findViewById(com.example.todayseat.R.id.linechart)

        val entries = mutableListOf<Entry>()  //차트 데이터 셋에 담겨질 데이터

        for (item in chartData) {
            entries.add(Entry(item.lableData.replace(("[^\\d.]").toRegex(), "").toFloat(), item.lineData.toFloat()))
        }

        //LineDataSet 선언
        val lineDataSet: LineDataSet
        lineDataSet = LineDataSet(entries, "라인챠트 예시")
        lineDataSet.color = Color.BLUE  //LineChart에서 Line Color 설정
        lineDataSet.setCircleColor(Color.DKGRAY)  // LineChart에서 Line Circle Color 설정
        lineDataSet.setCircleHoleColor(Color.DKGRAY) // LineChart에서 Line Hole Circle Color 설정

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(lineDataSet) // add the data sets

        // create a data object with the data sets
        val data = LineData(dataSets)

        // set data
        lineChart.setData(data)
        lineChart.setDescription(null); //차트에서 Description 설정 삭제

    }

    class LineMarkerView(context: Context?, layoutResource: Int) :
        MarkerView(context, layoutResource) {
        private val tvContent: TextView

        init {
            tvContent = findViewById(com.example.todayseat.R.id.tvContent)
        }

        // runs every time the MarkerView is redrawn, can be used to update the
        // content (user-interface)
        override fun refreshContent(e: Entry, highlight: Highlight?) {
            if (e is CandleEntry) {
                tvContent.setText(Utils.formatNumber(e.high, 0, true))
            } else {
                tvContent.setText(Utils.formatNumber(e.y, 0, true))
            }
            super.refreshContent(e, highlight)
        }

        override fun getOffset(): MPPointF {
            return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
        }
    }

}

 */