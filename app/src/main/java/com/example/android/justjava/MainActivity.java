/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        //      finds the whipped cream checkbox
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        //      the variable stores true if whipped cream checkbox is checked or false if it is not
        boolean stateOfWhippedCreamCheckBox = whippedCreamCheckBox.isChecked();

        //       finds the chocolate check box
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
//       the variable stores true if chocolate checkbox is checked or false if it is not
        boolean stateOfChocolateCheckBox = chocolateCheckBox.isChecked();

        EditText userNameEditText = findViewById(R.id.user_name_edit_text);
        String userName = userNameEditText.getText().toString();

//      Calculates the price of the order
        int price = calculatePrice(stateOfWhippedCreamCheckBox, stateOfChocolateCheckBox);
//      displays the Order Summary
        String priceMessage = createOrderSummary(price, stateOfWhippedCreamCheckBox, stateOfChocolateCheckBox, userName);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"kennethemmanuel28@gmail.com"});
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT,getResources().getString(R.string.email_subject),userName);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * This method is called when the increment button is clicked.
     */
    public void increment(View view) {

        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the decrement button is clicked.
     */
    public void decrement(View view) {

        if (quantity >= 1) {
            quantity = quantity - 1;
            displayQuantity(quantity);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Can not order less than 0 cups";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }


    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberToDisplay) {
        TextView quantityTextView   = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberToDisplay);
    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is true if the whipped cream check box is checked
     * @param addChocolate    is true if the chocolate check box is checked
     * @return the price of the number of coffee ordered
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int priceOfOneCup = 5;
//        the price of whipped cream is $1 per cup of coffee and add the price if user wants
        if (addWhippedCream) {
            priceOfOneCup += 1;
        }
//                the price of chocolate is $2 per cup of coffee and add the price if user wants
        if (addChocolate) {
            priceOfOneCup += 2;
        }
//        the total price of the cup of coffees
        return quantity * priceOfOneCup;
    }


    /*
     *creates the ordder summary for the customer
     *
     * @param priceOfOrder is calculated by calculatePrice() and passed in  createOrderSummary()
     *
     * @param addWhippedCream is wether or not the user wants chocolate topping
     *
     * @param addChocolate is whether or not the user wants chocolate topping
     *
     * @param userName is the name that the user typed in the edit text
     *
     * @return the order summaryto the user
     * */
    private String createOrderSummary(int priceOfOrder, boolean addWhippedCream, boolean addChocolate, String userName) {
        String orderSummary = userName + "" ;
        orderSummary += "\n" + getString(R.string.quantity_text_view)+ ": " + quantity;
        orderSummary += "\n" + getString(R.string.whipped_cream_addition) + "?" + addWhippedCream;
        orderSummary += "\n" + getString(R.string.chocolate_addition) + "?" + addChocolate;
        orderSummary += "\n" + getString(R.string.total)+ ": $" + priceOfOrder;
        orderSummary = orderSummary + "\n" + getString(R.string.thank_you);
        return orderSummary;
    }
}