package fr.inria.astor.approaches.jkali.operators;

import fr.inria.astor.approaches.jgenprog.operators.InsertBeforeOp;
import fr.inria.astor.approaches.jgenprog.operators.StatementLevelOperator;
import fr.inria.astor.core.entities.ModificationPoint;
import fr.inria.astor.core.entities.OperatorInstance;
import fr.inria.astor.core.entities.ProgramVariant;
import spoon.reflect.code.CtStatement;

/**
 * 
 * @author Matias Martinez
 *
 */
public class InsertReturnOp extends ReplaceReturnOp implements StatementLevelOperator {

	InsertBeforeOp insertOp = new InsertBeforeOp();

	@Override
	public boolean applyChangesInModel(OperatorInstance operation, ProgramVariant p) {

		return insertOp.applyChangesInModel(operation, p);

	}

	@Override
	public boolean undoChangesInModel(OperatorInstance opInstance, ProgramVariant p) {
		return insertOp.undoChangesInModel(opInstance, p);
	}

	@Override
	public boolean updateProgramVariant(OperatorInstance opInstance, ProgramVariant p) {
		return insertOp.updateProgramVariant(opInstance, p);
	}

	@Override
	public boolean canBeAppliedToPoint(ModificationPoint point) {

		return point.getCodeElement() instanceof CtStatement && !(point.getCodeElement().toString().startsWith("super")
				|| point.getCodeElement().toString().startsWith("<init>"));
	}

}
