package org.hans.lottonumberlottery

import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class MainActivity : AppCompatActivity() {

    private val clearButton: Button by lazy {
        findViewById<Button>(R.id.clearButton)
    }
    private val addButton: Button by lazy {
        findViewById<Button>(R.id.addButton)
    }
    private val runButton: Button by lazy {
        findViewById<Button>(R.id.runButton)
    }
    private val numberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker)
    }
    private val pickNumberList: List<TextView> by lazy {
        listOf<TextView>(
            findViewById<TextView>(R.id.pickNumber1),
            findViewById<TextView>(R.id.pickNumber2),
            findViewById<TextView>(R.id.pickNumber3),
            findViewById<TextView>(R.id.pickNumber4),
            findViewById<TextView>(R.id.pickNumber5),
            findViewById<TextView>(R.id.pickNumber6)
        )
    }
    private var didRun = false
    private val pickNumberSet = hashSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
    }

    // 자동 생성 시작 버튼 메서드
    private fun initRunButton() {
        runButton.setOnClickListener {
            val list = getRandomNumber()
        }
    }

    // 번호 추가하기 버튼 메서드
    private fun initAddButton() {
        addButton.setOnClickListener {
            // 번호를 추가할 수 없는 상태 예외처리
            if (didRun) {
                Toast.makeText(this, "초기화 후에 시도해주세요.", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }

            // 선택한 번호가 5개초과하면 예외처리
            // 설명 : 5개초과인데 부등호가 >=인 이유는 size가 0부터 시작하기 때문
            if (pickNumberSet.size >= 5) {
                Toast.makeText(this, "번호는 5개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }

            // 이미 선택한 번호를 다시 선택할 경우 예외처리
            if (pickNumberSet.contains(numberPicker.value)) {
                Toast.makeText(this, "이미 선택한 번호입니다.", Toast.LENGTH_SHORT).show()

                return@setOnClickListener
            }

            val pickNumber = pickNumberList[pickNumberSet.size]
            pickNumber.isVisible = true
            pickNumber.text = numberPicker.value.toString()

            pickNumberSet.add(numberPicker.value)
        }
    }

    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>().apply {
            for (i in 1..45) {
                this.add(i)
            }
        }

        numberList.shuffle()

        val newList = numberList.subList(0, 6)

        return newList.sorted()
    }
}