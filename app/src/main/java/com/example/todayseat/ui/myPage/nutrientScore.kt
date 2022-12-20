import android.database.Cursor
import android.graphics.Color
import android.graphics.DashPathEffect
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.todayseat.R
import com.example.todayseat.SplashActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.ChartData
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import java.util.*
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.util.*


class MPAndroidChartActivity : AppCompatActivity(), OnChartValueSelectedListener {

    lateinit var chart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_nutrient_score)

        chart = findViewById(R.id.nSTlinechart)
        chart.setBackgroundColor(Color.WHITE)
        chart.description.isEnabled = false
        chart.setTouchEnabled(true)
        chart.setOnChartValueSelectedListener(this)
        chart.setDrawGridBackground(false)
        chart.isDragEnabled = true
        chart.setScaleEnabled(true)
        chart.setPinchZoom(true)

        val xAxis = chart.xAxis
        xAxis.enableGridDashedLine(10f,10f,0f)

        val yAxis = chart.axisLeft
        chart.axisRight.isEnabled = false
        yAxis.enableGridDashedLine(10f, 10f, 0f)
        yAxis.setAxisMaximum(100f)
        yAxis.setAxisMinimum(0f)

        val llXAxis = LimitLine(9f, "Index 10")
        llXAxis.lineWidth = 4f
        llXAxis.enableDashedLine(10f, 10f, 0f)
        llXAxis.labelPosition = LimitLine.LimitLabelPosition.RIGHT_BOTTOM
        llXAxis.textSize = 10f
        //llXAxis.typeface = tfRegular

        val ll1 = LimitLine(100f, "Upper Limit")
        ll1.lineWidth = 4f
        ll1.enableDashedLine(10f, 10f, 0f)
        ll1.labelPosition = LimitLine.LimitLabelPosition.RIGHT_TOP
        ll1.textSize = 10f
        //ll1.typeface = tfRegular

        // draw limit lines behind data instead of on top
        yAxis.setDrawLimitLinesBehindData(true)
        xAxis.setDrawLimitLinesBehindData(true)

        // add limit lines
        yAxis.addLimitLine(ll1)

        setData(15, 100f)

        chart.animateX(1500)
        val legend = chart.legend
        legend.form = Legend.LegendForm.LINE

    }


    private fun setData(count: Int, range: Float) {

        val values = ArrayList<Entry>()

//        var sql = "SELECT * FROM NUTREINTSCORE"
//        var i: Float = 0F
//        val c: Cursor = SplashActivity.moappDB.rawQuery(sql,null)
//        while(c.moveToNext()){
////            val ID_pos = c.getColumnIndex("S_ID")
//            val score_pos = c.getColumnIndex("score")
//            val date_pos = c.getColumnIndex("S_date")
//            var score = c.getFloat(score_pos)
//            var sc_str = c.getString(date_pos)
//            values.add(Entry(i, score/*, resources.getDrawable(R.drawable.star)*/))
//            i++
//        }


        for (i in 0 until count) {

            val value = (Math.random() * range).toFloat()
            values.add(Entry(i.toFloat(), value/*, resources.getDrawable(R.drawable.star)*/))
        }

        val set1: LineDataSet

        if (chart.data != null && chart.data.dataSetCount > 0) {
            set1 = chart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            set1.notifyDataSetChanged()
            chart.data.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(values, "DataSet 1")

            set1.setDrawIcons(false)

            // draw dashed line
            set1.enableDashedLine(10f, 5f, 0f)

            // black lines and points
            set1.color = Color.BLACK
            set1.setCircleColor(Color.BLACK)

            // line thickness and point size
            set1.lineWidth = 1f
            set1.circleRadius = 3f

            // draw points as solid circles
            set1.setDrawCircleHole(false)

            // customize legend entry
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f

            // text size of values
            set1.valueTextSize = 9f

            // draw selection line as dashed
            set1.enableDashedHighlightLine(10f, 5f, 0f)

            // set the filled area
            set1.setDrawFilled(true)
            set1.fillFormatter = IFillFormatter { dataSet, dataProvider -> chart.axisLeft.axisMinimum }

            // set color of filled area
            /*if (Utils.getSDKInt() >= 18) {
                // drawables only supported on api level 18 and above
                val drawable = ContextCompat.getDrawable(this, R.drawable.fade_red)
                set1.fillDrawable = drawable
            } else {
                set1.fillColor = Color.BLACK
            }*/

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1) // add the data sets

            // create a data object with the data sets
            val data = LineData(dataSets)

            // set data
            chart.data = data
        }
    }


    override fun onNothingSelected() {
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
    }

}

/*
class MainActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName
    lateinit var lineChart: LineChart
    private val chartData = ArrayList<ChartData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chartData.clear()
        addChartItem("1월", 7.9)
        addChartItem("2월", 8.2)
        addChartItem("3월", 8.3)
        addChartItem("4월", 8.5)
        addChartItem("5월", 7.3)

        LineChartGraph(chartData, "강남")
    }

    private fun addChartItem(lableitem: String, dataitem: Double) {
        val item = ChartData()
        item.lableData = lableitem
        item.valData = dataitem
        chartData.add(item)
    }

    private fun LineChartGraph(chartItem: ArrayList<ChartData>, displayname: String) {
        lineChart = findViewById(R.id.linechart)

        val entries = ArrayList<Entry>()
        for (i in chartItem.indices) {
            entries.add(Entry(chartItem[i].valData.toFloat(), i))
        }

        val depenses = LineDataSet(entries, displayname)
        depenses.axisDependency = YAxis.AxisDependency.LEFT
        depenses.valueTextSize = 12f // 값 폰트 지정하여 크게 보이게 하기
        depenses.setColors(ColorTemplate.COLORFUL_COLORS) //
        //depenses.setDrawCubic(true); //선 둥글게 만들기
        depenses.setDrawFilled(false) //그래프 밑부분 색칠

        val labels = ArrayList<String>()
        for (i in chartItem.indices) {
            labels.add(chartItem[i].lableData)
        }

        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(depenses as ILineDataSet)
        val data = LineData(labels, dataSets) // 라이브러리 v3.x 사용하면 에러 발생함

        lineChart.data = data
        //lineChart.animateXY(1000,1000);
        lineChart.invalidate()
    }
}

/*
class MainActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName
    lateinit var lineChart: LineChart
    private val chartData = ArrayList<ChartData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // 서버에서 데이터 가져오기 (서버에서 가져온 데이터로 가정하고 직접 추가)
        chartData.clear()
        addChartItem("1월", 7.9)
        addChartItem("2월", 8.2)
        addChartItem("3월", 8.3)
        addChartItem("4월", 8.5)
        addChartItem("5월", 7.3)

        // 그래프 그릴 자료 넘기기
        LineChart(chartData)
    }

    private fun addChartItem(lableitem: String, dataitem: Double) {
        val item = ChartData()
        item.lableData = lableitem
        item.lineData = dataitem
        chartData.add(item)
    }

    private fun LineChart(chartData: ArrayList<ChartData>) {
        lineChart = findViewById(R.id.linechart)

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

}
 */
/*
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


    class TimeAxisValueFormat: IndexAxisValueFormatter(){
        override fun getFormattedValue(value: Float): String {

            var valueToMinutes = TimeUnit.MINUTES.toMillis(value.toLong())
            var timeMinutes = Date(valueToMinutes)
            var formatMinutes = SimpleDateFormat("Hm:mm")
            return super.getFormattedValue(value)
        }
    }
}

*/