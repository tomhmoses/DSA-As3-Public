package dsa_assignment3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import dsa_assignment3.PLTreeNodeTest;
import org.apache.log4j.Logger;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

public class PLTreeNodeTest
{
	private static final Logger	logger			= Logger.getLogger(PLTreeNodeTest.class);

	//@Rule
	//public Timeout				globalTimeout	= new Timeout(2000, TimeUnit.MILLISECONDS);

	@Test
	public void testCheckStudentIdentification()
	{
		assertNotEquals("Please update the studentID field in Drone.java with your student id number", //
				"MY STUDENT ID", dsa_assignment3.PLTreeNode.getStudentID());
		assertNotEquals("Please update the studentName field in Drone.java with your name", //
				"MY NAME", dsa_assignment3.PLTreeNode.getStudentName());
	}

	/**
	 * This is not a true JUnit Test method: it merely gives examples of calling the PLTree methods and obtaining output.
	 *
	 * As part of this exercise, you should devise your OWN tests to check that your code is performing as the JavaDoc and the assignment
	 * hand-out says it should. You are strongly advised to use the "Coverage" tool to check for parts of your code that are not executed as
	 * part of your tests. The coverage tool can be invoked from the "Run" menu item in Eclipse or from the button beside the "Run" and
	 * "Debug" buttons in Eclipse
	 */

	@Test
	public void testAlansCode()
	{
		NodeType[] typeList0 = { NodeType.A, NodeType.B };
		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList0);
		assertEquals("shouldn't work", null, pltree);

		NodeType[] typeList1 = { NodeType.A, NodeType.IMPLIES };
		pltree = PLTreeNode.reversePolishBuilder(typeList1);
		assertEquals("shouldn't work", null, pltree);

		pltree = PLTreeNode.reversePolishBuilder(null);
		assertEquals("shouldn't work", null, pltree);
	}

	@Test
	public void testToStringInfix()
	{
		NodeType[] typeList5 = { NodeType.A };
		PLTreeNodeInterface pltree5 = PLTreeNode.reversePolishBuilder(typeList5);
		assertEquals("Infix", "A", pltree5.toStringInfix());

		NodeType[] typeList6 = { NodeType.TRUE };
		PLTreeNodeInterface pltree6 = PLTreeNode.reversePolishBuilder(typeList6);
		assertEquals("Infix", "⊤", pltree6.toStringInfix());

		NodeType[] typeList7 = { NodeType.FALSE };
		PLTreeNodeInterface pltree7 = PLTreeNode.reversePolishBuilder(typeList7);
		assertEquals("Infix", "⊥", pltree7.toStringInfix());

		NodeType[] typeList8 = { NodeType.A, NodeType.NOT };
		PLTreeNodeInterface pltree8 = PLTreeNode.reversePolishBuilder(typeList8);
		assertEquals("Simple not Infix", "¬A", pltree8.toStringInfix());

		NodeType[] typeList = { NodeType.R, NodeType.P, NodeType.OR };
		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);
		assertEquals("Simple Or Infix", "(R∨P)", pltree.toStringInfix());

		NodeType[] typeList2 = { NodeType.R, NodeType.P, NodeType.AND };
		PLTreeNodeInterface pltree2 = PLTreeNode.reversePolishBuilder(typeList2);
		assertEquals("Simple And Infix", "(R∧P)", pltree2.toStringInfix());

		NodeType[] typeList4 = { NodeType.R, NodeType.P, NodeType.IMPLIES };
		PLTreeNodeInterface pltree4 = PLTreeNode.reversePolishBuilder(typeList4);
		assertEquals("Simple implies Infix", "(R→P)", pltree4.toStringInfix());

		NodeType[] typeList3 = { NodeType.R, NodeType.P, NodeType.AND, NodeType.Q, NodeType.OR };
		PLTreeNodeInterface pltree3 = PLTreeNode.reversePolishBuilder(typeList3);
		assertEquals("Simple Or + And Infix", "((R∧P)∨Q)", pltree3.toStringInfix());

	}

	@Test
	public void testToStringPrefix()
	{
		NodeType[] typeList5 = { NodeType.A };
		PLTreeNodeInterface pltree5 = PLTreeNode.reversePolishBuilder(typeList5);
		assertEquals("Prefix", "A", pltree5.toStringPrefix());

		NodeType[] typeList6 = { NodeType.TRUE };
		PLTreeNodeInterface pltree6 = PLTreeNode.reversePolishBuilder(typeList6);
		assertEquals("Prefix", "true", pltree6.toStringPrefix());

		NodeType[] typeList7 = { NodeType.FALSE };
		PLTreeNodeInterface pltree7 = PLTreeNode.reversePolishBuilder(typeList7);
		assertEquals("Prefix", "false", pltree7.toStringPrefix());

		NodeType[] typeList8 = { NodeType.A, NodeType.NOT };
		PLTreeNodeInterface pltree8 = PLTreeNode.reversePolishBuilder(typeList8);
		assertEquals("Prefix", "not(A)", pltree8.toStringPrefix());

		NodeType[] typeList = { NodeType.R, NodeType.P, NodeType.OR };
		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);
		assertEquals("Prefix", "or(R,P)", pltree.toStringPrefix());

		NodeType[] typeList2 = { NodeType.R, NodeType.P, NodeType.AND };
		PLTreeNodeInterface pltree2 = PLTreeNode.reversePolishBuilder(typeList2);
		assertEquals("Prefix", "and(R,P)", pltree2.toStringPrefix());

		NodeType[] typeList4 = { NodeType.R, NodeType.P, NodeType.IMPLIES };
		PLTreeNodeInterface pltree4 = PLTreeNode.reversePolishBuilder(typeList4);
		assertEquals("Prefix", "implies(R,P)", pltree4.toStringPrefix());

		NodeType[] typeList3 = { NodeType.R, NodeType.P, NodeType.AND, NodeType.Q, NodeType.OR };
		PLTreeNodeInterface pltree3 = PLTreeNode.reversePolishBuilder(typeList3);
		assertEquals("Prefix", "or(and(R,P),Q)", pltree3.toStringPrefix());

	}

	@Test
	public void testToString()
	{
		NodeType[] typeList0 = { NodeType.A, NodeType.B, NodeType.IMPLIES };
		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList0);
		assertEquals("toString should use toStringPrefix (according to the interface): ", "implies(A,B)", pltree.toString());
	}

	@Test
	public void testEliminateImplies()
	{
		NodeType[] typeList0 = { NodeType.A, NodeType.B, NodeType.IMPLIES };
		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList0);
		pltree.eliminateImplies();
		assertEquals("Simple implies: ", "(¬A∨B)", pltree.toStringInfix());


		NodeType[] typeList1 = { NodeType.A, NodeType.B, NodeType.IMPLIES, NodeType.C, NodeType.AND, NodeType.C, NodeType.IMPLIES, NodeType.C, NodeType.AND, NodeType.C, NodeType.AND };
		pltree = PLTreeNode.reversePolishBuilder(typeList1);
		pltree.eliminateImplies();
		assertEquals("Tests nested implies: ", "(((¬((¬A∨B)∧C)∨C)∧C)∧C)", pltree.toStringInfix());

	}

	@Test
	public void testApplyVarBindings()
	{
		NodeType[] typeList0 = { NodeType.A };
		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList0);
		Map<NodeType, Boolean> bindings = new HashMap<>();
		bindings.put(NodeType.A, true);
		pltree.applyVarBindings(bindings);
		assertEquals("true: ", "true", pltree.toStringPrefix());

		NodeType[] typeList1 = { NodeType.A, NodeType.B, NodeType.OR };
		pltree = PLTreeNode.reversePolishBuilder(typeList1);
		bindings = new HashMap<>();
		bindings.put(NodeType.A, true);
		bindings.put(NodeType.B, false);
		pltree.applyVarBindings(bindings);
		assertEquals("false: ", "or(true,false)", pltree.toStringPrefix());

		NodeType[] typeList2 = { NodeType.A, NodeType.B, NodeType.OR, NodeType.C, NodeType.AND };
		pltree = PLTreeNode.reversePolishBuilder(typeList2);
		bindings = new HashMap<>();
		bindings.put(NodeType.A, true);
		bindings.put(NodeType.B, false);
		pltree.applyVarBindings(bindings);
		assertEquals("nested apply var bindings: ", "and(or(true,false),C)", pltree.toStringPrefix());
	}

	@Test
	public void testPushNotDownMultipleNots()
	{
		NodeType[] typeList = { NodeType.R, NodeType.NOT, NodeType.NOT };
		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);
		assertEquals("Double Negation start", "¬¬R", pltree.toStringInfix());
		pltree.pushNotDown();
		assertEquals("Double Negation", "R", pltree.toStringInfix());

		NodeType[] typeList2 = { NodeType.R, NodeType.NOT, NodeType.NOT, NodeType.NOT, NodeType.NOT };
		PLTreeNodeInterface pltree2 = PLTreeNode.reversePolishBuilder(typeList2);
		pltree2.pushNotDown();
		assertEquals("4 * Negation", "R", pltree2.toStringInfix());

		NodeType[] typeList3 = { NodeType.R, NodeType.NOT, NodeType.NOT, NodeType.NOT, NodeType.NOT, NodeType.NOT, NodeType.NOT,
				NodeType.NOT };
		PLTreeNodeInterface pltree3 = PLTreeNode.reversePolishBuilder(typeList3);
		pltree3.pushNotDown();
		assertEquals("7 * Negation", "¬R", pltree3.toStringInfix());

	}

	@Test
	public void testPushNotDownDeMorgans()
	{
		NodeType[] typeList = { NodeType.A, NodeType.B, NodeType.OR, NodeType.NOT };
		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);
		assertEquals("NOT over OR start", "¬(A∨B)", pltree.toStringInfix());
		pltree.pushNotDown();
		assertEquals("NOT over OR", "(¬A∧¬B)", pltree.toStringInfix());

		NodeType[] typeList2 = { NodeType.A, NodeType.C, NodeType.IMPLIES, NodeType.B, NodeType.OR, NodeType.NOT };
		PLTreeNodeInterface pltree2 = PLTreeNode.reversePolishBuilder(typeList2);
		assertEquals("NOT over OR with deeper start", "¬((A→C)∨B)", pltree2.toStringInfix());
		pltree2.pushNotDown();
		assertEquals("NOT over OR with deeper", "(¬(A→C)∧¬B)", pltree2.toStringInfix());

		NodeType[] typeList3 = { NodeType.A, NodeType.B, NodeType.AND, NodeType.NOT };
		PLTreeNodeInterface pltree3 = PLTreeNode.reversePolishBuilder(typeList3);
		assertEquals("NOT over AND start", "¬(A∧B)", pltree3.toStringInfix());
		pltree3.pushNotDown();
		assertEquals("NOT over AND", "(¬A∨¬B)", pltree3.toStringInfix());

		NodeType[] typeList4 = { NodeType.A, NodeType.B, NodeType.OR, NodeType.NOT, NodeType.C, NodeType.AND, NodeType.NOT };
		PLTreeNodeInterface pltree4 = PLTreeNode.reversePolishBuilder(typeList4);
		assertEquals("NOT over AND + NOT over OR start", "¬(¬(A∨B)∧C)", pltree4.toStringInfix());
		pltree4.pushNotDown();
		assertEquals("NOT over AND + NOT over OR", "((A∨B)∨¬C)", pltree4.toStringInfix());

	}

	@Test
	public void testEvaluateConstantSubtreesSimple()
	{
		NodeType[] typeList = { NodeType.FALSE, NodeType.A, NodeType.AND };
		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);
		assertEquals("simple1 start", "(⊥∧A)", pltree.toStringInfix());
		pltree.evaluateConstantSubtrees();
		assertEquals("simple1 end", "⊥", pltree.toStringInfix());

		NodeType[] typeList1 = { NodeType.A, NodeType.TRUE, NodeType.AND};
		PLTreeNodeInterface pltree1 = PLTreeNode.reversePolishBuilder(typeList1);
		assertEquals("simple1 start", "(A∧⊤)", pltree1.toStringInfix());
		pltree1.evaluateConstantSubtrees();
		assertEquals("simple1 end", "A", pltree1.toStringInfix());

		NodeType[] typeList2 = { NodeType.FALSE, NodeType.A, NodeType.B, NodeType.OR, NodeType.AND };
		PLTreeNodeInterface pltree2 = PLTreeNode.reversePolishBuilder(typeList2);
		assertEquals("simple2 start", "(⊥∧(A∨B))", pltree2.toStringInfix());
		pltree2.evaluateConstantSubtrees();
		assertEquals("simple2 end", "⊥", pltree2.toStringInfix());
	}

	@Test
	public void testEvaluateConstantSubtreesNullChildren()
	{
		// TODO: if you can help me write a test for this that would be great

		/*
		NodeType[] typeList = { NodeType.FALSE, NodeType.A, NodeType.AND };
		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);
		assertEquals("NOT over OR start", "(⊥∧A)", pltree.toStringInfix());
		if (pltree.)
			assertEquals("NOT over OR", "⊥", pltree.toStringInfix());

		NodeType[] typeList2 = { NodeType.FALSE, NodeType.A, NodeType.B, NodeType.OR, NodeType.AND };
		PLTreeNodeInterface pltree2 = PLTreeNode.reversePolishBuilder(typeList2);
		assertEquals("NOT over OR start", "(⊥∧(A∨B))", pltree2.toStringInfix());
		pltree2.evaluateConstantSubtrees();
		assertEquals("NOT over OR", "⊥", pltree2.toStringInfix());
		*/
	}

	@Test
	public void testEvaluateConstantSubtreesReturns()
	{
		NodeType[] typeList = { NodeType.FALSE, NodeType.A, NodeType.AND };
		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);
		assertEquals("return1 start", "(⊥∧A)", pltree.toStringInfix());
		assertEquals("returns1", false, pltree.evaluateConstantSubtrees());

		NodeType[] typeList2 = { NodeType.TRUE, NodeType.B, NodeType.OR};
		PLTreeNodeInterface pltree2 = PLTreeNode.reversePolishBuilder(typeList2);
		assertEquals("return2 start", "(⊤∨B)", pltree2.toStringInfix());
		assertEquals("returns2", true, pltree2.evaluateConstantSubtrees());

		NodeType[] typeList3 = { NodeType.TRUE, NodeType.B, NodeType.OR, NodeType.C, NodeType.AND};
		PLTreeNodeInterface pltree3 = PLTreeNode.reversePolishBuilder(typeList3);
		assertEquals("return3 start", "((⊤∨B)∧C)", pltree3.toStringInfix());
		assertEquals("returns3", null, pltree3.evaluateConstantSubtrees());
	}

	@Test
	public void testEvaluateConstantSubtreesHard()
	{
		NodeType[] trueL = { NodeType.TRUE };
		PLTreeNodeInterface trueT = PLTreeNode.reversePolishBuilder(trueL);

		NodeType[] falseL = { NodeType.FALSE };
		PLTreeNodeInterface falseT = PLTreeNode.reversePolishBuilder(falseL);

		NodeType[] anyL = { NodeType.X, NodeType.Y, NodeType.Z, NodeType.OR, NodeType.OR, NodeType.W, NodeType.IMPLIES };
		PLTreeNodeInterface anyT = PLTreeNode.reversePolishBuilder(anyL);

		//⊤⊤
		NodeType[] typeList0a = { NodeType.TRUE, NodeType.TRUE, NodeType.AND };
		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList0a);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "⊤", pltree.toStringInfix());

		NodeType[] typeList0o = { NodeType.TRUE, NodeType.TRUE, NodeType.OR };
		pltree = PLTreeNode.reversePolishBuilder(typeList0o);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "⊤", pltree.toStringInfix());

		NodeType[] typeList0i = { NodeType.TRUE, NodeType.TRUE, NodeType.IMPLIES };
		pltree = PLTreeNode.reversePolishBuilder(typeList0i);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "⊤", pltree.toStringInfix());

		//⊤⊥
		NodeType[] typeList1a = { NodeType.TRUE, NodeType.FALSE, NodeType.AND };
		pltree = PLTreeNode.reversePolishBuilder(typeList1a);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "⊥", pltree.toStringInfix());

		NodeType[] typeList1o = { NodeType.TRUE, NodeType.FALSE, NodeType.OR };
		pltree = PLTreeNode.reversePolishBuilder(typeList1o);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "⊤", pltree.toStringInfix());

		NodeType[] typeList1i = { NodeType.TRUE, NodeType.FALSE, NodeType.IMPLIES };
		pltree = PLTreeNode.reversePolishBuilder(typeList1i);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "⊥", pltree.toStringInfix());

		//⊤N
		NodeType[] typeList2a = { NodeType.TRUE, NodeType.X, NodeType.Y, NodeType.Z, NodeType.OR, NodeType.OR, NodeType.W, NodeType.IMPLIES, NodeType.AND };
		pltree = PLTreeNode.reversePolishBuilder(typeList2a);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", anyT.toStringInfix(), pltree.toStringInfix());

		NodeType[] typeList2o = { NodeType.TRUE, NodeType.X, NodeType.Y, NodeType.Z, NodeType.OR, NodeType.OR, NodeType.W, NodeType.IMPLIES, NodeType.OR };
		pltree = PLTreeNode.reversePolishBuilder(typeList2o);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "⊤", pltree.toStringInfix());

		NodeType[] typeList2i = { NodeType.TRUE, NodeType.X, NodeType.Y, NodeType.Z, NodeType.OR, NodeType.OR, NodeType.W, NodeType.IMPLIES, NodeType.IMPLIES };
		pltree = PLTreeNode.reversePolishBuilder(typeList2i);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", anyT.toStringInfix(), pltree.toStringInfix());

		//⊥⊤
		NodeType[] typeList3a = { NodeType.FALSE, NodeType.TRUE, NodeType.AND };
		pltree = PLTreeNode.reversePolishBuilder(typeList3a);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "⊥", pltree.toStringInfix());

		NodeType[] typeList3o = { NodeType.FALSE, NodeType.TRUE, NodeType.OR };
		pltree = PLTreeNode.reversePolishBuilder(typeList3o);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "⊤", pltree.toStringInfix());

		NodeType[] typeList3i = { NodeType.FALSE, NodeType.TRUE, NodeType.IMPLIES };
		pltree = PLTreeNode.reversePolishBuilder(typeList3i);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "⊤", pltree.toStringInfix());

		//⊥⊥
		NodeType[] typeList4a = { NodeType.FALSE, NodeType.FALSE, NodeType.AND };
		pltree = PLTreeNode.reversePolishBuilder(typeList4a);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "⊥", pltree.toStringInfix());

		NodeType[] typeList4o = { NodeType.FALSE, NodeType.FALSE, NodeType.OR };
		pltree = PLTreeNode.reversePolishBuilder(typeList4o);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "⊥", pltree.toStringInfix());

		NodeType[] typeList4i = { NodeType.FALSE, NodeType.FALSE, NodeType.IMPLIES };
		pltree = PLTreeNode.reversePolishBuilder(typeList4i);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "⊤", pltree.toStringInfix());

		//⊥N
		NodeType[] typeList5a = { NodeType.FALSE, NodeType.X, NodeType.Y, NodeType.Z, NodeType.OR, NodeType.OR, NodeType.W, NodeType.IMPLIES, NodeType.AND };
		pltree = PLTreeNode.reversePolishBuilder(typeList5a);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "⊥", pltree.toStringInfix());

		NodeType[] typeList5o = { NodeType.FALSE, NodeType.X, NodeType.Y, NodeType.Z, NodeType.OR, NodeType.OR, NodeType.W, NodeType.IMPLIES, NodeType.OR };
		pltree = PLTreeNode.reversePolishBuilder(typeList5o);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", anyT.toStringInfix(), pltree.toStringInfix());

		NodeType[] typeList5i = { NodeType.FALSE, NodeType.X, NodeType.Y, NodeType.Z, NodeType.OR, NodeType.OR, NodeType.W, NodeType.IMPLIES, NodeType.IMPLIES };
		pltree = PLTreeNode.reversePolishBuilder(typeList5i);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "⊤", pltree.toStringInfix());

		//N⊤
		NodeType[] typeList6a = { NodeType.X, NodeType.Y, NodeType.Z, NodeType.OR, NodeType.OR, NodeType.W, NodeType.IMPLIES, NodeType.TRUE, NodeType.AND };
		pltree = PLTreeNode.reversePolishBuilder(typeList6a);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", anyT.toStringInfix(), pltree.toStringInfix());

		NodeType[] typeList6o = { NodeType.X, NodeType.Y, NodeType.Z, NodeType.OR, NodeType.OR, NodeType.W, NodeType.IMPLIES, NodeType.TRUE, NodeType.OR };
		pltree = PLTreeNode.reversePolishBuilder(typeList6o);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "⊤", pltree.toStringInfix());

		NodeType[] typeList6i = { NodeType.X, NodeType.Y, NodeType.Z, NodeType.OR, NodeType.OR, NodeType.W, NodeType.IMPLIES, NodeType.TRUE, NodeType.IMPLIES };
		pltree = PLTreeNode.reversePolishBuilder(typeList6i);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "⊤", pltree.toStringInfix());

		//N⊥
		NodeType[] typeList7a = { NodeType.X, NodeType.Y, NodeType.Z, NodeType.OR, NodeType.OR, NodeType.W, NodeType.IMPLIES, NodeType.FALSE, NodeType.AND };
		pltree = PLTreeNode.reversePolishBuilder(typeList7a);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "⊥", pltree.toStringInfix());

		NodeType[] typeList7o = { NodeType.X, NodeType.Y, NodeType.Z, NodeType.OR, NodeType.OR, NodeType.W, NodeType.IMPLIES, NodeType.FALSE, NodeType.OR };
		pltree = PLTreeNode.reversePolishBuilder(typeList7o);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", anyT.toStringInfix(), pltree.toStringInfix());

		NodeType[] typeList7i = { NodeType.X, NodeType.Y, NodeType.Z, NodeType.OR, NodeType.OR, NodeType.W, NodeType.IMPLIES, NodeType.FALSE, NodeType.IMPLIES };
		pltree = PLTreeNode.reversePolishBuilder(typeList7i);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "¬" + anyT.toStringInfix(), pltree.toStringInfix());

		//NN
		NodeType[] typeList8a = { NodeType.A, NodeType.A, NodeType.AND };
		pltree = PLTreeNode.reversePolishBuilder(typeList8a);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "(A∧A)", pltree.toStringInfix());

		NodeType[] typeList8o = { NodeType.A, NodeType.A, NodeType.OR };
		pltree = PLTreeNode.reversePolishBuilder(typeList8o);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "(A∨A)", pltree.toStringInfix());

		NodeType[] typeList8i = { NodeType.A, NodeType.A, NodeType.IMPLIES };
		pltree = PLTreeNode.reversePolishBuilder(typeList8i);
		pltree.evaluateConstantSubtrees();
		assertEquals("Refer to table: ", "(A→A)", pltree.toStringInfix());

	}

	@Test
	public void testPushOrBelowAnd()
	{
		NodeType[] typeList = { NodeType.P, NodeType.Q, NodeType.AND, NodeType.R, NodeType.OR };
		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);
		assertEquals("AND LHS", "((P∧Q)∨R)", pltree.toStringInfix());
		pltree.pushOrBelowAnd();
		assertEquals("AND LHS", "((P∨R)∧(Q∨R))", pltree.toStringInfix());

		NodeType[] typeList2 = { NodeType.P, NodeType.Q, NodeType.R, NodeType.AND, NodeType.OR};
		PLTreeNodeInterface pltree2 = PLTreeNode.reversePolishBuilder(typeList2);
		assertEquals("AND RHS", "(P∨(Q∧R))", pltree2.toStringInfix());
		pltree2.pushOrBelowAnd();
		assertEquals("AND RHS", "((P∨Q)∧(P∨R))", pltree2.toStringInfix());

		NodeType[] typeList5 = { NodeType.FALSE, NodeType.NOT, NodeType.TRUE, NodeType.NOT, NodeType.AND, NodeType.TRUE, NodeType.Q,
				NodeType.NOT, NodeType.AND, NodeType.OR };
		PLTreeNodeInterface pltree5 = PLTreeNode.reversePolishBuilder(typeList5);
		assertEquals("complicated start", "((¬⊥∧¬⊤)∨(⊤∧¬Q))", pltree5.toStringInfix());
		pltree5.pushOrBelowAnd();
		assertEquals("complicated", "(((¬⊥∨⊤)∧(¬⊤∨⊤))∧((¬⊥∨¬Q)∧(¬⊤∨¬Q)))", pltree5.toStringInfix());
	}

	@Test
	public void testMakeAndOrRightDeep()
	{
		// (X∨Y)∨Z with X∨(Y∨Z)
		// (X∧Y)∧Z with X∧(Y∧Z)
		NodeType[] typeList1 = new NodeType[] { NodeType.X, NodeType.Y, NodeType.OR, NodeType.Z, NodeType.OR};
		PLTreeNodeInterface pltree1 = PLTreeNode.reversePolishBuilder(typeList1);
		assertEquals("Constructed: " ,"((X∨Y)∨Z)",pltree1.toStringInfix());
		pltree1.makeAndOrRightDeep();
		assertEquals("Made : " ,"(X∨(Y∨Z))",pltree1.toStringInfix());


		NodeType[] typeList2 = new NodeType[] { NodeType.X, NodeType.Y, NodeType.AND, NodeType.Z, NodeType.AND};
		PLTreeNodeInterface pltree2 = PLTreeNode.reversePolishBuilder(typeList2);
		assertEquals("Constructed: " ,"((X∧Y)∧Z)",pltree2.toStringInfix());
		pltree2.makeAndOrRightDeep();
		assertEquals("Made : " ,"(X∧(Y∧Z))",pltree2.toStringInfix());

		// (W∨X)∨(Y∨Z) into W∨(X∨(Y∨Z))
		// ((W∨X)∨Y)∨Z into W∨(X∨(Y∨Z))
		NodeType[] typeList3 = new NodeType[] { NodeType.W, NodeType.X, NodeType.OR, NodeType.Y, NodeType.Z, NodeType.OR, NodeType.OR};
		NodeType[] typeList4 = new NodeType[] { NodeType.W, NodeType.X, NodeType.OR, NodeType.Y, NodeType.OR, NodeType.Z, NodeType.OR };
		pltree2 = PLTreeNode.reversePolishBuilder(typeList3);
		assertEquals("Constructed: " ,"((W∨X)∨(Y∨Z))",pltree2.toStringInfix());
		pltree2.makeAndOrRightDeep();
		assertEquals("Made1 : " ,"(W∨(X∨(Y∨Z)))",pltree2.toStringInfix());

		pltree2 = PLTreeNode.reversePolishBuilder(typeList4);
		assertEquals("Constructed: " ,"(((W∨X)∨Y)∨Z)",pltree2.toStringInfix());
		pltree2.makeAndOrRightDeep();
		assertEquals("Made2 : " ,"(W∨(X∨(Y∨Z)))",pltree2.toStringInfix());
	}

	@Test
	public void testNodeReferences() {
		NodeType[] typeList1 = new NodeType[] { NodeType.A, NodeType.B, NodeType.IMPLIES, NodeType.C, NodeType.D, NodeType.AND, NodeType.OR };
		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList1);
		pltree.pushOrBelowAnd();

	}


	@Test
	public void woooEverythingWorks()
	{
		NodeType[] typeList = { NodeType.R, NodeType.P, NodeType.OR, NodeType.TRUE, NodeType.Q, NodeType.NOT, NodeType.AND,
				NodeType.IMPLIES };
		assertEquals("typeList: ", "[R, P, OR, TRUE, Q, NOT, AND, IMPLIES]", Arrays.toString(typeList));

		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

		assertNotNull("PLTree construction failed when using: " + typeList, pltree);
		assertEquals("Constructed: ", "implies(or(R,P),and(true,not(Q)))", pltree.toStringPrefix());
		assertEquals("Constructed: ", "((R∨P)→(⊤∧¬Q))", pltree.toStringInfix());
		NodeType[] typeListReturned = pltree.getReversePolish();
		assertEquals("typeListReturned: ", "[R, P, OR, TRUE, Q, NOT, AND, IMPLIES]", Arrays.toString(typeListReturned));

		Map<NodeType, Boolean> bindings = new HashMap<>();
		bindings.put(NodeType.P, true);
		bindings.put(NodeType.R, false);

		pltree.applyVarBindings(bindings);
		assertEquals("Applied bindings : ", "implies(or(false,true),and(true,not(Q)))",
				pltree.toStringPrefix());
		assertEquals("Applied bindings : ", "((⊥∨⊤)→(⊤∧¬Q))", pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		assertEquals("typeListReturned: ", "[FALSE, TRUE, OR, TRUE, Q, NOT, AND, IMPLIES]", Arrays.toString(typeListReturned));

		//    Boolean b = pltree.evaluateConstantSubtrees();
		//    assertEquals(String.format("Evaluate constant subtrees to get: %s", pltree.toStringPrefix()));
		//    assertEquals(String.format("Evaluate constant subtrees to get: %s", pltree.toStringInfix()));

		pltree.eliminateImplies();
		assertEquals("Eliminate Implies: %s", "or(not(or(false,true)),and(true,not(Q)))", pltree.toStringPrefix());
		assertEquals("Eliminate Implies: %s", "(¬(⊥∨⊤)∨(⊤∧¬Q))", pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		assertEquals("typeListReturned: ", "[FALSE, TRUE, OR, NOT, TRUE, Q, NOT, AND, OR]", Arrays.toString(typeListReturned));

		pltree.pushNotDown();
		assertEquals("pushNotDown: ", "or(and(not(false),not(true)),and(true,not(Q)))", pltree.toStringPrefix());
		assertEquals("pushNotDown: ", "((¬⊥∧¬⊤)∨(⊤∧¬Q))", pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		assertEquals("typeListReturned: ", "[FALSE, NOT, TRUE, NOT, AND, TRUE, Q, NOT, AND, OR]", Arrays.toString(typeListReturned));

		pltree.pushOrBelowAnd();
		/*
		assertEquals("pushOrBelowAnd: ", "and(or(not(false),and(true,not(Q))),or(not(true),and(true,not(Q))))", pltree.toStringPrefix());
		assertEquals("pushOrBelowAnd: ", "((¬⊥∨(⊤∧¬Q))∧(¬⊤∨(⊤∧¬Q)))", pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		assertEquals("typeListReturned: ", "[FALSE, NOT, TRUE, Q, NOT, AND, OR, TRUE, NOT, TRUE, Q, NOT, AND, OR, AND]",
				Arrays.toString(typeListReturned));
		*/
		NodeType[] typeList2 = { NodeType.FALSE, NodeType.NOT, NodeType.TRUE, NodeType.Q, NodeType.NOT, NodeType.AND, NodeType.OR,
				NodeType.TRUE, NodeType.NOT, NodeType.TRUE, NodeType.Q, NodeType.NOT, NodeType.AND, NodeType.OR, NodeType.AND };
		pltree = PLTreeNode.reversePolishBuilder(typeList2);

		pltree.makeAndOrRightDeep();
		assertEquals("makeAndOrRightDeep: ", "and(or(not(false),and(true,not(Q))),or(not(true),and(true,not(Q))))",
				pltree.toStringPrefix());
		assertEquals("makeAndOrRightDeep: ", "((¬⊥∨(⊤∧¬Q))∧(¬⊤∨(⊤∧¬Q)))", pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		assertEquals("typeListReturned: ", "[FALSE, NOT, TRUE, Q, NOT, AND, OR, TRUE, NOT, TRUE, Q, NOT, AND, OR, AND]",
				Arrays.toString(typeListReturned));

		pltree.evaluateConstantSubtrees();
		assertEquals("Evaluate constant subtrees to get: ", "not(Q)", pltree.toStringPrefix());
		assertEquals("Evaluate constant subtrees to get: ", "¬Q", pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		assertEquals("typeListReturned: ", "[Q, NOT]", Arrays.toString(typeListReturned));

		//==========================

		typeList = new NodeType[] { NodeType.R, NodeType.P, NodeType.IMPLIES, NodeType.S, NodeType.IMPLIES, NodeType.NOT, NodeType.Q,
				NodeType.IMPLIES };
		assertEquals("typeList: ", "[R, P, IMPLIES, S, IMPLIES, NOT, Q, IMPLIES]", Arrays.toString(typeList));

		pltree = PLTreeNode.reversePolishBuilder(typeList);
		assertEquals("Constructed: ", "implies(not(implies(implies(R,P),S)),Q)", pltree.toStringPrefix());
		assertEquals("Constructed: ", "(¬((R→P)→S)→Q)", pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		assertEquals("typeListReturned: ", "[R, P, IMPLIES, S, IMPLIES, NOT, Q, IMPLIES]", Arrays.toString(typeListReturned));

		pltree.reduceToCNF();
		assertEquals("ReduceToCNF to get: ", "and(or(R,or(S,Q)),or(not(P),or(S,Q)))", pltree.toStringPrefix());
		assertEquals("ReduceToCNF to get: ", "((R∨(S∨Q))∧(¬P∨(S∨Q)))", pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		assertEquals("typeListReturned: ", "[R, S, Q, OR, OR, P, NOT, S, Q, OR, OR, AND]", Arrays.toString(typeListReturned));

		pltree.evaluateConstantSubtrees();
		assertEquals("Evaluate constant subtrees to get: ", "and(or(R,or(S,Q)),or(not(P),or(S,Q)))", pltree.toStringPrefix());
		assertEquals("Evaluate constant subtrees to get: ", "((R∨(S∨Q))∧(¬P∨(S∨Q)))", pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		assertEquals("typeListReturned: ", "[R, S, Q, OR, OR, P, NOT, S, Q, OR, OR, AND]", Arrays.toString(typeListReturned));

		//==========================

		typeList = new NodeType[] { NodeType.A, NodeType.B, NodeType.AND, NodeType.C, NodeType.OR, NodeType.D, NodeType.OR, NodeType.E,
				NodeType.OR, NodeType.F, NodeType.OR, NodeType.G, NodeType.OR, NodeType.H, NodeType.OR };
		assertEquals("typeList: ", "[A, B, AND, C, OR, D, OR, E, OR, F, OR, G, OR, H, OR]", Arrays.toString(typeList));
		pltree = PLTreeNode.reversePolishBuilder(typeList);
		assertEquals("Constructed: ", "or(or(or(or(or(or(and(A,B),C),D),E),F),G),H)", pltree.toStringPrefix());
		assertEquals("Constructed: ", "(((((((A∧B)∨C)∨D)∨E)∨F)∨G)∨H)", pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		assertEquals("typeListReturned: ", "[A, B, AND, C, OR, D, OR, E, OR, F, OR, G, OR, H, OR]", Arrays.toString(typeListReturned));

		pltree.pushOrBelowAnd();
		assertEquals("pushOrBelowAnd: %s", "and(or(or(or(or(or(or(A,C),D),E),F),G),H),or(or(or(or(or(or(B,C),D),E),F),G),H))",
				pltree.toStringPrefix());
		assertEquals("pushOrBelowAnd: %s", "(((((((A∨C)∨D)∨E)∨F)∨G)∨H)∧((((((B∨C)∨D)∨E)∨F)∨G)∨H))", pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		assertEquals("typeListReturned: ",
				"[A, C, OR, D, OR, E, OR, F, OR, G, OR, H, OR, B, C, OR, D, OR, E, OR, F, OR, G, OR, H, OR, AND]",
				Arrays.toString(typeListReturned));

		//===========================
	}


	@Test
	public void extraTestsForPushOrBelowAnd()
	{

		//"Extra tests of pushOrBelowAnd()"
		NodeType[] typeList = new NodeType[] { NodeType.A, NodeType.B, NodeType.AND, NodeType.C, NodeType.AND, NodeType.D, NodeType.OR, NodeType.E, NodeType.OR};
		assertEquals("typeList: " , "[A, B, AND, C, AND, D, OR, E, OR]",Arrays.toString(typeList));
		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);
		assertEquals("Constructed: " ,"or(or(and(and(A,B),C),D),E)",pltree.toStringPrefix());
		assertEquals("Constructed: " ,"((((A∧B)∧C)∨D)∨E)",pltree.toStringInfix());
		NodeType[] typeListReturned = pltree.getReversePolish();
		assertEquals("typeListReturned: " ,"[A, B, AND, C, AND, D, OR, E, OR]",Arrays.toString(typeListReturned));

		pltree.pushOrBelowAnd();
		//assertEquals("pushOrBelowAnd: ", "", pltree.toStringPrefix());
		assertEquals("pushOrBelowAnd: ", "((((A∨D)∨E)∧((B∨D)∨E))∧((C∨D)∨E))", pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		assertEquals("typeListReturned: " ,"[A, D, OR, E, OR, B, D, OR, E, OR, AND, C, D, OR, E, OR, AND]",Arrays.toString(typeListReturned));

		pltree.makeAndOrRightDeep();
		assertEquals("makeAndOrRightDeep: ", "((A∨(D∨E))∧((B∨(D∨E))∧(C∨(D∨E))))", pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		assertEquals("typeListReturned: " ,"[A, D, E, OR, OR, B, D, E, OR, OR, C, D, E, OR, OR, AND, AND]",Arrays.toString(typeListReturned));

	}


	@Test
	public void testPLTreeConstruction()
	{
		NodeType[] typeList = { NodeType.R, NodeType.P, NodeType.OR, NodeType.TRUE, NodeType.Q, NodeType.NOT, NodeType.AND,
				NodeType.IMPLIES };
		assertEquals("1. typeList: ", "[R, P, OR, TRUE, Q, NOT, AND, IMPLIES]", Arrays.toString(typeList));

		PLTreeNodeInterface pltree = PLTreeNode.reversePolishBuilder(typeList);

		assertNotNull("PLTree construction failed when using: " + typeList, pltree);
		assertEquals("2. Constructed: ", "implies(or(R,P),and(true,not(Q)))", pltree.toStringPrefix());
		assertEquals("3. Constructed: ", "((R∨P)→(⊤∧¬Q))", pltree.toStringInfix());
		NodeType[] typeListReturned = pltree.getReversePolish();
		assertEquals("typeListReturned: ", "[R, P, OR, TRUE, Q, NOT, AND, IMPLIES]", Arrays.toString(typeListReturned));

		Map<NodeType, Boolean> bindings = new HashMap<>();
		bindings.put(NodeType.P, true);
		bindings.put(NodeType.R, false);

		pltree.applyVarBindings(bindings);
		logger.debug(String.format("Applied bindings : %s to get: %s", bindings, pltree.toStringPrefix()));
		logger.debug(String.format("Applied bindings : %s to get: %s", bindings, pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("7. typeListReturned: " + Arrays.toString(typeListReturned));

		//		Boolean b = pltree.evaluateConstantSubtrees();
		//		logger.debug(String.format("Evaluate constant subtrees to get: %s", pltree.toStringPrefix()));
		//		logger.debug(String.format("Evaluate constant subtrees to get: %s", pltree.toStringInfix()));

		pltree.eliminateImplies();
		logger.debug(String.format("Eliminate Implies: %s", pltree.toStringPrefix()));
		logger.debug(String.format("Eliminate Implies: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("10. typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.pushNotDown();
		logger.debug(String.format("pushNotDown: %s", pltree.toStringPrefix()));
		logger.debug(String.format("pushNotDown: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("13. typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.pushOrBelowAnd();
		logger.debug(String.format("pushOrBelowAnd: %s", pltree.toStringPrefix()));
		logger.debug(String.format("pushOrBelowAnd: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("16. typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.makeAndOrRightDeep();
		logger.debug(String.format("makeAndOrRightDeep: %s", pltree.toStringPrefix()));
		logger.debug(String.format("makeAndOrRightDeep: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("19. typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.evaluateConstantSubtrees();
		logger.debug(String.format("Evaluate constant subtrees to get: %s", pltree.toStringPrefix()));
		logger.debug(String.format("Evaluate constant subtrees to get: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("22. typeListReturned: " + Arrays.toString(typeListReturned));

		//==========================

		typeList = new NodeType[] { NodeType.R, NodeType.P, NodeType.IMPLIES, NodeType.S, NodeType.IMPLIES, NodeType.NOT, NodeType.Q,
				NodeType.IMPLIES };
		logger.debug("23. typeList: " + Arrays.toString(typeList));

		pltree = PLTreeNode.reversePolishBuilder(typeList);
		logger.debug("Constructed: " + pltree.toStringPrefix());
		logger.debug("Constructed: " + pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		logger.debug("27. typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.reduceToCNF();
		logger.debug(String.format("ReduceToCNF to get: %s", pltree.toStringPrefix()));
		logger.debug(String.format("ReduceToCNF to get: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("30. typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.evaluateConstantSubtrees();
		logger.debug(String.format("Evaluate constant subtrees to get: %s", pltree.toStringPrefix()));
		logger.debug(String.format("Evaluate constant subtrees to get: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("33. typeListReturned: " + Arrays.toString(typeListReturned));

		//==========================

		typeList = new NodeType[] { NodeType.A, NodeType.B, NodeType.AND, NodeType.C, NodeType.OR, NodeType.D, NodeType.OR, NodeType.E,
				NodeType.OR, NodeType.F, NodeType.OR, NodeType.G, NodeType.OR, NodeType.H, NodeType.OR };
		logger.debug("typeList: " + Arrays.toString(typeList));
		pltree = PLTreeNode.reversePolishBuilder(typeList);
		logger.debug("Constructed: " + pltree.toStringPrefix());
		logger.debug("Constructed: " + pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		logger.debug("37. typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.pushOrBelowAnd();
		logger.debug(String.format("pushOrBelowAnd: %s", pltree.toStringPrefix()));
		logger.debug(String.format("pushOrBelowAnd: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("40. typeListReturned: " + Arrays.toString(typeListReturned));

		//===========================

		logger.debug("Extra tests of pushOrBelowAnd()");
		typeList = new NodeType[] { NodeType.A, NodeType.B, NodeType.AND, NodeType.C, NodeType.AND, NodeType.D, NodeType.OR, NodeType.E,
				NodeType.OR };
		logger.debug("typeList: " + Arrays.toString(typeList));
		pltree = PLTreeNode.reversePolishBuilder(typeList);
		logger.debug("Constructed: " + pltree.toStringPrefix());
		logger.debug("Constructed: " + pltree.toStringInfix());
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

		pltree.pushOrBelowAnd();
		logger.debug(String.format("pushOrBelowAnd: %s", pltree.toStringPrefix()));
		logger.debug(String.format("pushOrBelowAnd: %s", pltree.toStringInfix()));
		typeListReturned = pltree.getReversePolish();
		logger.debug("typeListReturned: " + Arrays.toString(typeListReturned));

	}
	/*
	 To assist you in writing your tests, the log output from the solution to
	 the exercise for the above code is included below. You may find it useful
	 to copy and paste some of the strings below into some of your tests.
	
		VEERSION 2: after correcting an error in pushOrBelowAnd()
	 
	 1. typeList: [R, P, OR, TRUE, Q, NOT, AND, IMPLIES]
	 2. Constructed: implies(or(R,P),and(true,not(Q)))
	 3. Constructed: ((R∨P)→(⊤∧¬Q))
	 4. typeListReturned: [R, P, OR, TRUE, Q, NOT, AND, IMPLIES]
	 5. Applied bindings : {P=true, R=false} to get: implies(or(false,true),and(true,not(Q)))
	 6. Applied bindings : {P=true, R=false} to get: ((⊥∨⊤)→(⊤∧¬Q))
	 7. typeListReturned: [FALSE, TRUE, OR, TRUE, Q, NOT, AND, IMPLIES]
	 8. Eliminate Implies: or(not(or(false,true)),and(true,not(Q)))
	 9. Eliminate Implies: (¬(⊥∨⊤)∨(⊤∧¬Q))
	 10. typeListReturned: [FALSE, TRUE, OR, NOT, TRUE, Q, NOT, AND, OR]
	 11. pushNotDown: or(and(not(false),not(true)),and(true,not(Q)))
	 12. pushNotDown: ((¬⊥∧¬⊤)∨(⊤∧¬Q))
	 13. typeListReturned: [FALSE, NOT, TRUE, NOT, AND, TRUE, Q, NOT, AND, OR]
	 14. pushOrBelowAnd: and(or(not(false),and(true,not(Q))),or(not(true),and(true,not(Q))))
	 15. pushOrBelowAnd: ((¬⊥∨(⊤∧¬Q))∧(¬⊤∨(⊤∧¬Q)))
	 16. typeListReturned: [FALSE, NOT, TRUE, Q, NOT, AND, OR, TRUE, NOT, TRUE, Q, NOT, AND, OR, AND]
	 17. makeAndOrRightDeep: and(or(not(false),and(true,not(Q))),or(not(true),and(true,not(Q))))
	 18. makeAndOrRightDeep: ((¬⊥∨(⊤∧¬Q))∧(¬⊤∨(⊤∧¬Q)))
	 19. typeListReturned: [FALSE, NOT, TRUE, Q, NOT, AND, OR, TRUE, NOT, TRUE, Q, NOT, AND, OR, AND]
	 20. Evaluate constant subtrees to get: not(Q)
	 21. Evaluate constant subtrees to get: ¬Q
	 22. typeListReturned: [Q, NOT]
	 23. typeList: [R, P, IMPLIES, S, IMPLIES, NOT, Q, IMPLIES]
	 24. typeListReturned: [Q, NOT]
	 25. Constructed: implies(not(implies(implies(R,P),S)),Q)
	 26. Constructed: (¬((R→P)→S)→Q)
	 27. typeListReturned: [R, P, IMPLIES, S, IMPLIES, NOT, Q, IMPLIES]
	 28. ReduceToCNF to get: and(or(R,or(S,Q)),or(not(P),or(S,Q)))
	 29. ReduceToCNF to get: ((R∨(S∨Q))∧(¬P∨(S∨Q)))
	 30. typeListReturned: [R, S, Q, OR, OR, P, NOT, S, Q, OR, OR, AND]
	 . Evaluate constant subtrees to get: and(or(R,or(S,Q)),or(not(P),or(S,Q)))
	 . Evaluate constant subtrees to get: ((R∨(S∨Q))∧(¬P∨(S∨Q)))
	 . typeListReturned: [R, S, Q, OR, OR, P, NOT, S, Q, OR, OR, AND]
	 . typeList: [A, B, AND, C, OR, D, OR, E, OR, F, OR, G, OR, H, OR]
	 35. Constructed: or(or(or(or(or(or(and(A,B),C),D),E),F),G),H)
	 . Constructed: (((((((A∧B)∨C)∨D)∨E)∨F)∨G)∨H)
	 . typeListReturned: [A, B, AND, C, OR, D, OR, E, OR, F, OR, G, OR, H, OR]
	 . pushOrBelowAnd: and(or(or(or(or(or(or(A,C),D),E),F),G),H),or(or(or(or(or(or(B,C),D),E),F),G),H))
	 . pushOrBelowAnd: (((((((A∨C)∨D)∨E)∨F)∨G)∨H)∧((((((B∨C)∨D)∨E)∨F)∨G)∨H))
	 40. typeListReturned: [A, C, OR, D, OR, E, OR, F, OR, G, OR, H, OR, B, C, OR, D, OR, E, OR, F, OR, G, OR, H, OR, AND]
	
	
	
	*/

}





