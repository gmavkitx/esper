/*
 ***************************************************************************************
 *  Copyright (C) 2006 EsperTech, Inc. All rights reserved.                            *
 *  http://www.espertech.com/esper                                                     *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 ***************************************************************************************
 */
package com.espertech.esper.common.internal.context.aifactory.ontrigger.ontrigger;

import com.espertech.esper.common.client.EventType;
import com.espertech.esper.common.internal.bytecodemodel.base.CodegenClassScope;
import com.espertech.esper.common.internal.bytecodemodel.base.CodegenMethod;
import com.espertech.esper.common.internal.bytecodemodel.model.expression.CodegenExpressionRef;
import com.espertech.esper.common.internal.context.activator.ViewableActivatorForge;
import com.espertech.esper.common.internal.context.aifactory.core.SAIFFInitializeSymbol;
import com.espertech.esper.common.internal.epl.expression.subquery.ExprSubselectNode;
import com.espertech.esper.common.internal.epl.expression.table.ExprTableAccessNode;
import com.espertech.esper.common.internal.epl.lookupplansubord.SubordinateWMatchExprQueryPlanForge;
import com.espertech.esper.common.internal.epl.namedwindow.path.NamedWindowMetaData;
import com.espertech.esper.common.internal.epl.subselect.SubSelectFactoryForge;
import com.espertech.esper.common.internal.epl.table.compiletime.TableMetaData;
import com.espertech.esper.common.internal.epl.table.strategy.ExprTableEvalStrategyFactoryForge;
import com.espertech.esper.common.internal.epl.updatehelper.EventBeanUpdateHelperForge;

import java.util.Map;

public class StatementAgentInstanceFactoryOnTriggerInfraUpdateForge extends StatementAgentInstanceFactoryOnTriggerInfraBaseForge {
    private final EventBeanUpdateHelperForge updateHelperForge;

    public StatementAgentInstanceFactoryOnTriggerInfraUpdateForge(ViewableActivatorForge activator, EventType resultEventType, Map<ExprSubselectNode, SubSelectFactoryForge> subselects, Map<ExprTableAccessNode, ExprTableEvalStrategyFactoryForge> tableAccesses, String nonSelectRSPProviderClassName, NamedWindowMetaData namedWindow, TableMetaData table, SubordinateWMatchExprQueryPlanForge queryPlanForge, EventBeanUpdateHelperForge updateHelperForge) {
        super(activator, resultEventType, subselects, tableAccesses, nonSelectRSPProviderClassName, namedWindow, table, queryPlanForge);
        this.updateHelperForge = updateHelperForge;
    }

    public Class typeOfSubclass() {
        return StatementAgentInstanceFactoryOnTriggerInfraUpdate.class;
    }

    protected void inlineInitializeOnTriggerSpecific(CodegenExpressionRef saiff, CodegenMethod method, SAIFFInitializeSymbol symbols, CodegenClassScope classScope) {
        if (namedWindow != null) {
            method.getBlock().exprDotMethod(saiff, "setUpdateHelperNamedWindow", updateHelperForge.makeWCopy(method, classScope));
        } else {
            method.getBlock()
                    .exprDotMethod(saiff, "setUpdateHelperTable", updateHelperForge.makeNoCopy(method, classScope));
        }
    }
}
