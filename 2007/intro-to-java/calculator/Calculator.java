//--------------------------------------------------------
//Lab 6: Building a Graphical-User Interface With NetBeans
//--------------------------------------------------------

/*
 * Calculator.java
 *
 * Created on October 24, 2006, 10:31 PM
 */
public class Calculator extends javax.swing.JFrame {

	private double operandA;
	private double operandB;
	private char operator;

    /** Creates new form Calculator */
    public Calculator() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        display = new javax.swing.JTextField();
        seven = new javax.swing.JButton();
        eight = new javax.swing.JButton();
        nine = new javax.swing.JButton();
        divide = new javax.swing.JButton();
        four = new javax.swing.JButton();
        five = new javax.swing.JButton();
        six = new javax.swing.JButton();
        multiply = new javax.swing.JButton();
        one = new javax.swing.JButton();
        two = new javax.swing.JButton();
        three = new javax.swing.JButton();
        minus = new javax.swing.JButton();
        zero = new javax.swing.JButton();
        clear = new javax.swing.JButton();
        equal = new javax.swing.JButton();
        add = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("My  Calculator");
        display.setFont(new java.awt.Font("Comic Sans MS", 0, 14));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(display, gridBagConstraints);

        seven.setText("7");
        seven.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sevenButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(seven, gridBagConstraints);

        eight.setText("8");
        eight.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eightButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(eight, gridBagConstraints);

        nine.setText("9");
        nine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nineButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(nine, gridBagConstraints);

        divide.setText("/");
        divide.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                divideButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(divide, gridBagConstraints);

        four.setText("4");
        four.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fourButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(four, gridBagConstraints);

        five.setText("5");
        five.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fiveButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(five, gridBagConstraints);

        six.setText("6");
        six.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sixButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(six, gridBagConstraints);

        multiply.setText("*");
        multiply.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                multiplyButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(multiply, gridBagConstraints);

        one.setText("1");
        one.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                oneButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(one, gridBagConstraints);

        two.setText("2");
        two.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twoButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(two, gridBagConstraints);

        three.setText("3");
        three.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                threeButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(three, gridBagConstraints);

        minus.setText("-");
        minus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minusButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(minus, gridBagConstraints);

        zero.setText("0");
        zero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zeroButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(zero, gridBagConstraints);

        clear.setText("C");
        clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(clear, gridBagConstraints);

        equal.setText("=");
        equal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equalButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(equal, gridBagConstraints);

        add.setText("+");
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonHandler(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(add, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void addButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonHandler
// TODO add your handling code here:

		operandA = Double.parseDouble(display.getText());
		operator = '+';
		display.setText("");

    }//GEN-LAST:event_addButtonHandler

    private void equalButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_equalButtonHandler
// TODO add your handling code here:

		operandB = Double.parseDouble(display.getText());

		switch(operator){
			case '*': operandA *= operandB;
					  display.setText("" + operandA);
					  break;

			case '+': operandA += operandB;
					  display.setText("" + operandA);
					  break;

			case '/': if(operandB==0)
						display.setText("Do Not Divide By Zero");
					  else{
						operandA /= operandB;
					  	display.setText("" + operandA);
				  	  }
					  break;

			case '-': operandA -= operandB;
					  display.setText("" + operandA);
					  break;

			default:
		}

    }//GEN-LAST:event_equalButtonHandler

    private void clearButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonHandler
// TODO add your handling code here:

			display.setText("");

    }//GEN-LAST:event_clearButtonHandler

    private void zeroButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zeroButtonHandler
// TODO add your handling code here:

		String current = display.getText();
		if(current.equals("0"))
			display.setText("0");
		else
		{
			StringBuffer tmp = new StringBuffer(current);
			display.setText("" + tmp.append("0"));
		}

    }//GEN-LAST:event_zeroButtonHandler

    private void minusButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minusButtonHandler
// TODO add your handling code here:

		operandA = Double.parseDouble(display.getText());
		operator = '-';
		display.setText("");

    }//GEN-LAST:event_minusButtonHandler

    private void threeButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_threeButtonHandler
// TODO add your handling code here:

		StringBuffer current = new StringBuffer(display.getText());
		display.setText("" + current.append("3"));

    }//GEN-LAST:event_threeButtonHandler

    private void twoButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twoButtonHandler
// TODO add your handling code here:

		StringBuffer current = new StringBuffer(display.getText());
		display.setText("" + current.append("2"));

    }//GEN-LAST:event_twoButtonHandler

    private void oneButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_oneButtonHandler
// TODO add your handling code here:

		StringBuffer current = new StringBuffer(display.getText());
		display.setText("" + current.append("1"));

    }//GEN-LAST:event_oneButtonHandler

    private void multiplyButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_multiplyButtonHandler
// TODO add your handling code here:

		operandA = Double.parseDouble(display.getText());
		operator = '*';
		display.setText("");

    }//GEN-LAST:event_multiplyButtonHandler

    private void sixButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sixButtonHandler
// TODO add your handling code here:

		StringBuffer current = new StringBuffer(display.getText());
		display.setText("" + current.append("6"));

    }//GEN-LAST:event_sixButtonHandler

    private void fiveButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fiveButtonHandler
// TODO add your handling code here:

		StringBuffer current = new StringBuffer(display.getText());
		display.setText("" + current.append("5"));

    }//GEN-LAST:event_fiveButtonHandler

    private void fourButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fourButtonHandler
// TODO add your handling code here:

		StringBuffer current = new StringBuffer(display.getText());
		display.setText("" + current.append("4"));

    }//GEN-LAST:event_fourButtonHandler

    private void divideButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_divideButtonHandler
// TODO add your handling code here:

		operandA = Double.parseDouble(display.getText());
		operator = '/';
		display.setText("");


    }//GEN-LAST:event_divideButtonHandler

    private void nineButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nineButtonHandler
// TODO add your handling code here:

		StringBuffer current = new StringBuffer(display.getText());
		display.setText("" + current.append("9"));

    }//GEN-LAST:event_nineButtonHandler

    private void eightButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eightButtonHandler
// TODO add your handling code here:

		StringBuffer current = new StringBuffer(display.getText());
		display.setText("" + current.append("8"));

    }//GEN-LAST:event_eightButtonHandler

    private void sevenButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sevenButtonHandler
// TODO add your handling code here:

		StringBuffer current = new StringBuffer(display.getText());
		display.setText("" + current.append("7"));

    }//GEN-LAST:event_sevenButtonHandler

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Calculator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton add;
    private javax.swing.JButton clear;
    private javax.swing.JTextField display;
    private javax.swing.JButton divide;
    private javax.swing.JButton eight;
    private javax.swing.JButton equal;
    private javax.swing.JButton five;
    private javax.swing.JButton four;
    private javax.swing.JButton minus;
    private javax.swing.JButton multiply;
    private javax.swing.JButton nine;
    private javax.swing.JButton one;
    private javax.swing.JButton seven;
    private javax.swing.JButton six;
    private javax.swing.JButton three;
    private javax.swing.JButton two;
    private javax.swing.JButton zero;
    // End of variables declaration//GEN-END:variables

}
