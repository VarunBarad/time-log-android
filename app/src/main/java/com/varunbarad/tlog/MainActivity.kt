package com.varunbarad.tlog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.varunbarad.tlog.databinding.ActivityMainBinding
import com.varunbarad.tlog.external_services.local_database.models.DbLogEntry
import com.varunbarad.tlog.models.EntryCategory
import com.varunbarad.tlog.util.Dependencies
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private val serviceDisposables = CompositeDisposable()

    private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

    private lateinit var viewBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(viewBinding.toolbar)
    }

    override fun onStart() {
        super.onStart()

        val repository = Dependencies.getLogEntryRepository(context = this)

        viewBinding.buttonAddEntryIntentional.setOnClickListener {
            val now = LocalDateTime.now()
            serviceDisposables.add(
                repository.insertNewLogEntry(
                    logEntry = DbLogEntry(
                        id = UUID.randomUUID(),
                        category = EntryCategory.INTENTIONAL,
                        startTime = now.minusMinutes(15),
                        endTime = now,
                    ),
                ).subscribeBy {
                    Snackbar.make(
                        this.viewBinding.root,
                        "Intentional entry logged",
                        Snackbar.LENGTH_SHORT,
                    ).show()
                },
            )
        }
        viewBinding.buttonAddEntryUnintentional.setOnClickListener {
            val now = LocalDateTime.now()
            serviceDisposables.add(
                repository.insertNewLogEntry(
                    logEntry = DbLogEntry(
                        id = UUID.randomUUID(),
                        category = EntryCategory.UNINTENTIONAL,
                        startTime = now.minusMinutes(15),
                        endTime = now,
                    ),
                ).subscribeBy {
                    Snackbar.make(
                        this.viewBinding.root,
                        "Unintentional entry logged",
                        Snackbar.LENGTH_SHORT,
                    ).show()
                },
            )
        }

        serviceDisposables.add(
            repository.getAllEntriesSortedReverseChronologicallyByStartTime()
                .subscribeBy { entries ->
                    val now = LocalDateTime.now()

                    val (intentionalEntries, unintentionalEntries) = entries
                        .filter { it.endTime > now.minusDays(1) }
                        .partition { it.category == EntryCategory.INTENTIONAL }

                    viewBinding.textViewIntentional.text =
                        "Intentional entries: ${intentionalEntries.size}"
                    viewBinding.textViewUnintentional.text =
                        "Unintentional entries: ${unintentionalEntries.size}"

                    val lastEntry = entries.maxByOrNull { it.endTime }

                    val lastEntryText = if (lastEntry != null) {
                        val lastEntryWasToday = lastEntry.endTime.toLocalDate() == now.toLocalDate()
                        val lastEntryWasYesterday = lastEntry.endTime.toLocalDate().plusDays(1) == now.toLocalDate()

                        when {
                            lastEntryWasToday -> timeFormatter.format(lastEntry.endTime)
                            lastEntryWasYesterday -> "Yesterday, ${timeFormatter.format(lastEntry.endTime)}"
                            else -> {
                                val daysOfDifference = ChronoUnit.DAYS.between(
                                    lastEntry.endTime.toLocalDate(),
                                    now.toLocalDate(),
                                )

                                "$daysOfDifference days ago, ${timeFormatter.format(lastEntry.endTime)}"
                            }
                        }
                    } else {
                        "No entries logged"
                    }

                    viewBinding.textViewLastEntryTime.text = "Last entry: $lastEntryText"
                }
        )
    }

    override fun onStop() {
        super.onStop()

        viewBinding.buttonAddEntryIntentional.setOnClickListener(null)
        viewBinding.buttonAddEntryUnintentional.setOnClickListener(null)

        serviceDisposables.clear()
    }
}
