package com.ej.skeletonviewtest

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

class MainActivity : AppCompatActivity() {

    var skeleton = true

    lateinit var adapter : SectionAdapter
    val sectionList = listOf(
        SectionInfo.Section1Info(),
        SectionInfo.Section2Info(),
        SectionInfo.Section3Info(),
        SectionInfo.Section4Info()
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn = findViewById<Button>(R.id.button)
        val rv = findViewById<RecyclerView>(R.id.rv)

        adapter = SectionAdapter()
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter


        adapter.submitList(sectionList)



        CoroutineScope(Dispatchers.Default).launch {
            delay(3000)
            updateSection(sectionList, SectionInfo.Section1Info(loading = false))

        }
    }

    override fun onStart() {
        super.onStart()

    }

    fun updateSection(sectionList: List<SectionInfo>, sectionInfo: SectionInfo) {
        val currentList = sectionList.toMutableList()  // 현재 리스트를 가변 리스트로 복사

        when (sectionInfo) {
            is SectionInfo.Section1Info -> {
                // Section1Info에 대한 처리 로직
                val index = currentList.indexOfFirst { it is SectionInfo.Section1Info }
                if (index != -1) {
                    currentList[index] = sectionInfo  // 해당 섹션의 데이터를 새 데이터로 업데이트
                    adapter.submitList(currentList)
                }
            }

            is SectionInfo.Section2Info -> {
                // Section2Info에 대한 처리 로직
                println("Handling Section2Info with type: ${sectionInfo.type}")
            }

            is SectionInfo.Section3Info -> {
                // Section3Info에 대한 처리 로직
                println("Handling Section3Info with type: ${sectionInfo.type}")
            }

            is SectionInfo.Section4Info -> {
                // Section4Info에 대한 처리 로직
                println("Handling Section4Info with type: ${sectionInfo.type}")
            }

            else -> {
                println("Handling unknown type of SectionInfo")
            }
        }
//

    }

    fun findFirstIndexOfType(sectionList: List<SectionInfo>, sectionType: KClass<out SectionInfo>): Int {
        // 리스트에서 특정 타입의 첫 인덱스를 찾습니다
        return sectionList.indexOfFirst { it::class == sectionType }
    }
}