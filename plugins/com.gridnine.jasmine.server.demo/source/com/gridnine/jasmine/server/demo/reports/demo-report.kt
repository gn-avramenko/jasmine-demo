/*****************************************************************
 * Gridnine AB http://www.gridnine.com
 * Project: Jasmine
 *****************************************************************/

package com.gridnine.jasmine.server.demo.reports

import com.gridnine.jasmine.common.core.model.ObjectReference
import com.gridnine.jasmine.common.core.storage.*
import com.gridnine.jasmine.common.demo.model.domain.DemoComplexDocumentIndex
import com.gridnine.jasmine.common.demo.model.domain.DemoUserAccount
import com.gridnine.jasmine.common.reports.model.misc.*
import com.gridnine.jasmine.common.standard.model.l10n.StandardL10nMessagesFactory
import com.gridnine.jasmine.server.demo.model.reports.DemoReportParametersIds
import com.gridnine.jasmine.server.demo.model.reports.DemoReportType
import com.gridnine.jasmine.server.reports.builders.report
import com.gridnine.jasmine.server.reports.model.*
import com.gridnine.jasmine.server.reports.model.EndDateReportRequestedParameterHandler
import java.math.BigDecimal
import java.time.LocalDate

object DemoUserReportRequestedParameterHandler : BaseObjectReferenceReportRequestedParameterHandler<DemoUserAccount>(DemoReportParametersIds.USER, DemoUserAccount::class)

class DemoReportServerHandler : BaseSeverReportHandler(DemoReportType.DEMO_REPORT) {

    override fun getRequestedParametersDescriptions(): List<ReportRequestedParameterDescription> {
        return arrayListOf(StartDateReportRequestedParameterHandler,
                EndDateReportRequestedParameterHandler,
                DemoUserReportRequestedParameterHandler).map { it.createParameterDescription() }
    }

    override fun generateReport(parameters: List<BaseReportRequestedParameter>): ReportGenerationResult {
        val query = searchQuery {
            where {
                StartDateReportRequestedParameterHandler.getValue(parameters)?.let {
                    ge(DemoComplexDocumentIndex.datePropertyProperty, it)
                }
                EndDateReportRequestedParameterHandler.getValue(parameters)?.let {
                    le(DemoComplexDocumentIndex.datePropertyProperty, it)
                }
                DemoUserReportRequestedParameterHandler.getValue(parameters)?.let {
                    eq(DemoComplexDocumentIndex.entityRefPropertyProperty, it)
                }
            }
        }
        val map = linkedMapOf<ObjectReference<DemoUserAccount>?, DemoReportBatch>()
        Storage.get().searchDocuments(DemoComplexDocumentIndex::class, query).forEach { idx ->
            map.getOrPut(idx.entityRefProperty) {
                DemoReportBatch(idx.entityRefProperty?.caption)
            }.entries.add(DemoReportEntry(idx.enumProperty?.toString(), idx.dateProperty, idx.floatProperty))
        }
        val lst = map.values.sortedBy { it.userName ?: "" }.toMutableList()
        lst.forEach { it.entries.sortBy { entry -> entry.dateValue } }
        val report = report {
            fileName = "demo.xlsx"
            val titleStyle = style {
                fontBold = true
                horizontalAlignment = GeneratedReportCellHorizontalAlignment.CENTER
                fontHeight = 20
            }
            val parameterNameStyle = style {
                fontBold = true
                fontHeight = 14
                horizontalAlignment = GeneratedReportCellHorizontalAlignment.RIGHT
            }
            val parameterValueDateStyle = style {
                fontHeight = 14
                horizontalAlignment = GeneratedReportCellHorizontalAlignment.LEFT
                format = "yyyy.MM.dd"
            }
            val parameterValueRefStyle = style {
                fontHeight = 14
                horizontalAlignment = GeneratedReportCellHorizontalAlignment.LEFT
            }
            val headerStyle = style {
                fontHeight = 12
                horizontalAlignment = GeneratedReportCellHorizontalAlignment.CENTER
                verticalAlignment = GeneratedReportCellVerticalAlignment.CENTER
                bottomBorderWidth = GeneratedReportCellBorderWidth.THICK
                topBorderWidth = GeneratedReportCellBorderWidth.THICK
                leftBorderWidth = GeneratedReportCellBorderWidth.THICK
                rightBorderWidth = GeneratedReportCellBorderWidth.THICK
                foregroundColor = GeneratedReportColor.GREY_25_PERCENT
                wrapText = true
            }

            val userDataStyle = style {
                fontHeight = 12
                horizontalAlignment = GeneratedReportCellHorizontalAlignment.CENTER
                verticalAlignment = GeneratedReportCellVerticalAlignment.CENTER
                bottomBorderWidth = GeneratedReportCellBorderWidth.THIN
                topBorderWidth = GeneratedReportCellBorderWidth.THIN
                leftBorderWidth = GeneratedReportCellBorderWidth.THIN
                rightBorderWidth = GeneratedReportCellBorderWidth.THIN
            }

            val stringDataStyle = style {
                parentStyle = userDataStyle
            }
            val dateDataStyle = style {
                parentStyle = userDataStyle
                format = "yyyy.MM.dd"
            }
            val numberDataStyle = style {
                parentStyle = userDataStyle
                horizontalAlignment = GeneratedReportCellHorizontalAlignment.RIGHT
                format = "0.#"
            }
            val totalTextStyle = style {
                parentStyle = userDataStyle
                horizontalAlignment = GeneratedReportCellHorizontalAlignment.RIGHT
                fontBold = true
            }
            val totalNumberStyle = style {
                parentStyle = numberDataStyle
                horizontalAlignment = GeneratedReportCellHorizontalAlignment.RIGHT
                fontBold = true
            }
            list {
                defaultRowHeight = 20
                title = "лист 1"
                columns {
                    column(30)
                    column(20)
                    column(20)
                    column(20)
                }
                row(25)
                text("Демонстрационный отчет", titleStyle, 4, 1)
                row(25)
                row()
                text("Дата начала:", parameterNameStyle)
                date(StartDateReportRequestedParameterHandler.getValue(parameters), parameterValueDateStyle)
                row()
                text("Дата окончания:", parameterNameStyle)
                date(EndDateReportRequestedParameterHandler.getValue(parameters), parameterValueDateStyle)
                row()
                text("Пользователь:", parameterNameStyle)
                text(DemoUserReportRequestedParameterHandler.getValue(parameters)?.caption, parameterValueRefStyle)
                row()
                row(25)
                text("Пользователь", headerStyle)
                text("Перечисление", headerStyle)
                text("Дата", headerStyle)
                text("Число", headerStyle)
                if (lst.isNotEmpty()) {
                    var totalNumber = BigDecimal.ZERO
                    val subTotalIndexes = arrayListOf<Int>()

                    lst.forEach { batch ->
                        val batchStartRow = getCurrentRowIndex() + 1
                        var batchTotalNumber: BigDecimal = 0.toBigDecimal()
                        batch.entries.withIndex().forEach { (index, entry) ->
                            row()
                            text(batch.userName, stringDataStyle, 1, if (index == 0) batch.entries.size + 1 else 1)
                            text(entry.enumValue, stringDataStyle)
                            date(entry.dateValue, dateDataStyle)
                            number(entry.numberValue, numberDataStyle)
                            batchTotalNumber = batchTotalNumber.add(entry.numberValue ?: 0.toBigDecimal())
                        }
                        row()
                        emptyCell()
                        text("Итого", totalTextStyle, 2, 1)
                        emptyCell()
                        numberFormula("SUM(D${batchStartRow + 1}:D${getCurrentRowIndex()})", batchTotalNumber, totalNumberStyle)
                        subTotalIndexes.add(getCurrentRowIndex())
                        totalNumber = totalNumber.add(batchTotalNumber)
                    }
                    row()
                    text("Итого", totalTextStyle, 3, 1)
                    emptyCell()
                    emptyCell()
                    numberFormula("SUM(${subTotalIndexes.joinToString(",") { "D$it" }})", totalNumber, totalNumberStyle)
                }
            }
        }
        return ReportGenerationResult().let {
            it.report = report
            it
        }
    }

    override fun validate(params: List<BaseReportRequestedParameter>): Map<String, String?>? {
        StartDateReportRequestedParameterHandler.getValue(params)
                ?: return mapOf(StartDateReportRequestedParameterHandler.getId() to StandardL10nMessagesFactory.Empty_field())
        return null
    }


}

class DemoReportEntry(val enumValue: String?, val dateValue: LocalDate?, val numberValue: BigDecimal?)

class DemoReportBatch(val userName: String?, val entries: MutableList<DemoReportEntry> = arrayListOf())