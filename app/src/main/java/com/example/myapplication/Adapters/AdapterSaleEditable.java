package com.example.myapplication.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Activities.SellProductActivity;
import com.example.myapplication.Fragments.SellProductFragment;
import com.example.myapplication.Models.ModelProduct;
import com.example.myapplication.Models.ModelSale;
import com.example.myapplication.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import cn.pedant.SweetAlert.SweetAlertDialog;
import java.util.List;

public class AdapterSaleEditable extends FirebaseRecyclerAdapter<ModelSale, AdapterSaleEditable.ViewHolder> {
    private Context context;
    private List<ModelSale> modelSaleList;
    private boolean discountFlag = false;
    private boolean priceEvenFlag = false;
    private ViewHolder mHolder;
    private Activity activity;

    public AdapterSaleEditable(@NonNull FirebaseRecyclerOptions<ModelSale> options) {
        super(options);

    }

    @NonNull
    @Override
    public AdapterSaleEditable.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_sale_editable, parent, false);
        return new ViewHolder(view);
    }


    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull ModelSale model) {
        String productId = model.getProductId();
        String productCategory = model.getProductCategory();
        String productName = model.getProductName();
        String productSize = model.getProductSize();
        String productBrand = model.getProductBrand();
        int productPrice = model.getProductPrice();
        int productQuantity = model.getProductQuantity();
        String productManufacture = model.getProductManufacture();
        String productExpire = model.getProductExpire();
        String createdAt = model.getCreatedAt();

        holder.tvProductName.setText(productName);
        holder.tvProductSize.setText("(" + productSize + ")");
        holder.tvProductCategory.setText(productCategory);
        holder.tvProductPrice.setText(String.valueOf(productPrice));
        holder.inputTotalPrice.setText(String.valueOf(productPrice));
        holder.inputSaleQuantity.setText(String.valueOf(1));
        holder.inputSalePrice.setText(String.valueOf(productPrice));
        holder.inputSaleDiscount.setText(String.valueOf(0));
        holder.tvProductBrand.setText(productBrand);
        holder.tvProductManufacture.setText(productManufacture);
        holder.tvProductExpire.setText(productExpire);
        holder.tvCount.setText(String.valueOf(position + 1));

        holder.btnUpdate.setOnClickListener(v->updateSaleRecord(holder,holder.getAdapterPosition()));
        //holder.sumColumn();


    }

    private void updateSaleRecord(ViewHolder holder, int position)
    {
        holder.btnUpdate.setEnabled(false);
        int quantity = 1;
        int discount = 0;
        int price = 0;
        String strQuantity = holder.inputSaleQuantity.getText().toString().trim();
        String strDiscount = holder.inputSaleDiscount.getText().toString().trim();
        String strPrice = holder.inputSalePrice.getText().toString().trim();

        if (strQuantity.isEmpty())
        {
            holder.inputSaleQuantity.setError("Enter Sale Quantity");
            holder.inputSaleQuantity.requestFocus();
            holder.btnUpdate.setEnabled(true);
            return;
        }
        else
            quantity = Integer.parseInt(strQuantity);

        if (!strPrice.isEmpty())
            price = Integer.parseInt(strPrice);

        if (!strDiscount.isEmpty())
            discount = Integer.parseInt(strDiscount);

        int productQuantity = modelSaleList.get(holder.getAdapterPosition()).getProductQuantity();

        if (quantity > productQuantity+1)
        {
            alertLowQuantity(holder);
            holder.btnUpdate.setEnabled(true);
            return;
        }

    }

    public void alertLowQuantity(ViewHolder holder) {
        String productName = modelSaleList.get(holder.getAdapterPosition()).getProductName();
        int productQuantity = modelSaleList.get(holder.getAdapterPosition()).getProductQuantity()+1;
        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        sweetAlertDialog.setTitleText("The Available Quantity Of "+productName+" is "+productQuantity+" . Please Decrease The Quantity.");
        sweetAlertDialog.show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvProductName, tvProductSize, tvProductCategory, tvSaleTime, tvProductPrice, tvProductBrand, tvProductManufacture, tvProductExpire, tvCount;
        private EditText inputSaleQuantity, inputSaleDiscount, inputSalePrice, inputTotalPrice;
        private CardView cvSell;
        private int position;
        private TextWatcher quantityWatcher;
        private TextWatcher priceWatcher;
        private TextWatcher discountWatcher;
        private Button btnCancelUpdate, btnUpdate;


        private void registerTextWatchers() {
            Log.d("SocialCodia", "Registring Listener");
            inputSaleQuantity.addTextChangedListener(quantityWatcher);
            inputSalePrice.addTextChangedListener(priceWatcher);
            inputSaleDiscount.addTextChangedListener(discountWatcher);
        }

        private void unregisterTextWatcher() {
            Log.d("SocialCodia", "UnRegistering Listener");
            inputSaleQuantity.removeTextChangedListener(quantityWatcher);
            inputSalePrice.removeTextChangedListener(priceWatcher);
            inputSaleDiscount.removeTextChangedListener(discountWatcher);
        }

        // Ask a question about multiple casting on stack. till then create a new adapter class
        // for selltoseller and use that adapter for that. Thanks
        /*
        public void sumColumn()
        {
            int salePrice = 0;
            int totalPrice = 0;
            for (ModelSale sale : modelSaleList)
            {
                salePrice = salePrice + sale.getSalePrice();
                totalPrice = totalPrice + sale.getProductTotalPrice();
            }
            ((SellProductActivity)activity).updateTotalValue(totalPrice,salePrice);
        }

         */

        private void priceEvent() {
            Log.d("SocialCodia", "PriceEvent Method Called");
            unregisterTextWatcher();
            int totalPrice = Integer.parseInt(inputTotalPrice.getText().toString().trim());
            String sellPriceString = inputSalePrice.getText().toString().trim();
            if (sellPriceString.trim().length() > 0) {
                int sellPrice = Integer.parseInt(inputSalePrice.getText().toString().trim());
                int discount = percentage(sellPrice, totalPrice);
                inputSaleDiscount.setText(String.valueOf(discount));
                modelSaleList.get(getAdapterPosition()).setProductTotalPrice(totalPrice);
                modelSaleList.get(getAdapterPosition()).setSalePrice(sellPrice);
                //sumColumn();
            } else {
                inputSaleDiscount.setText(String.valueOf(100));
            }
            registerTextWatchers();
        }

        private void quantityEvent() {
            Log.d("SocialCodia", "quantityEvent Method Called");
            unregisterTextWatcher();
            String quan = inputSaleQuantity.getText().toString().trim();
            String per = inputSaleDiscount.getText().toString().trim();
            int quantity;
            int percentage;
            if (quan.equals("0"))
            {
                inputSaleQuantity.setText("1");
                quan = "1";
            }
            if (quan == null || quan.length() < 1 || quan.isEmpty())
                quantity = 1;
            else
                quantity = Integer.parseInt(quan);
            if (per == null || per.length() < 1)
                percentage = 0;
            else
                percentage = Integer.parseInt(per);
            int price = Integer.parseInt(tvProductPrice.getText().toString());
            int finalPrice = price * quantity;
            inputTotalPrice.setText(String.valueOf(finalPrice));
//            modelSaleList.get(getAdapterPosition()).set(salePrice);
            int salePrice = percentageDec(finalPrice, percentage);
            modelSaleList.get(getAdapterPosition()).setSalePrice(salePrice);
            inputSalePrice.setText(String.valueOf(salePrice));
            modelSaleList.get(getAdapterPosition()).setSalePrice(salePrice);
            modelSaleList.get(getAdapterPosition()).setProductTotalPrice(finalPrice);
            //sumColumn();
            registerTextWatchers();
        }

        private void discountInputEvent() {
            Log.d("SocialCodia", "discountInputEvent Method Called");
            unregisterTextWatcher();
            int totalPrice = Integer.parseInt(inputTotalPrice.getText().toString().trim());
            String sellPriceString = inputSalePrice.getText().toString().trim();
            int salePrice = 0;
            if (sellPriceString.trim().length()>0)
            {
                salePrice = Integer.parseInt(sellPriceString);
            }
            String per = inputSaleDiscount.getText().toString().trim();
            int percentage;
            if (per == null || per.length() < 1)
                percentage = 0;
            else
                percentage = Integer.parseInt(per);
            int price = percentageDec(totalPrice, percentage);
            inputSalePrice.setText(String.valueOf(price));
            modelSaleList.get(getAdapterPosition()).setSalePrice(price);
            modelSaleList.get(getAdapterPosition()).setProductTotalPrice(totalPrice);
            //sumColumn();
            registerTextWatchers();
        }

        private int percentage(int partialValue, int totalValue) {
            Log.d("SocialCodia", "percentage Method Called");
            Double partial = (double) partialValue;
            Double total = (double) totalValue;
            Double per = (100 * partial) / total;
            Double p = 100 - per;
            return p.intValue();
        }

        private int percentageDec(int totalValue, int per) {
            Log.d("SocialCodia", "percentageDec Method Called");
            if (per == 0 || String.valueOf(per).length() < 0)
                return totalValue;
            else {
                Double total = (double) totalValue;
                Double perc = (double) per;
                Double price = (total - ((perc / 100) * total));
                Integer p = price.intValue();
                return p;
            }
        }

        private void showActionButton()
        {
            btnCancelUpdate.setVisibility(View.VISIBLE);
            btnUpdate.setVisibility(View.VISIBLE);
        }

        private void hideActionButton()
        {
            btnCancelUpdate.setVisibility(View.GONE);
            btnUpdate.setVisibility(View.GONE);
        }



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductSize = itemView.findViewById(R.id.tvProductSize);
            tvProductCategory = itemView.findViewById(R.id.tvProductCategory);
            tvSaleTime = itemView.findViewById(R.id.tvSaleTime);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            inputSaleQuantity = itemView.findViewById(R.id.inputSaleQuantity);
            inputSaleDiscount = itemView.findViewById(R.id.inputSaleDiscount);
            inputSalePrice = itemView.findViewById(R.id.inputSalePrice);
            tvProductBrand = itemView.findViewById(R.id.tvProductBrand);
            tvProductManufacture = itemView.findViewById(R.id.tvProductManufacture);
            tvProductExpire = itemView.findViewById(R.id.tvProductExpire);
            inputTotalPrice = itemView.findViewById(R.id.inputTotalPrice);
            tvCount = itemView.findViewById(R.id.tvCount);
            cvSell = itemView.findViewById(R.id.cvSell);
            btnCancelUpdate = itemView.findViewById(R.id.btnCancelUpdate);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);


        }
    }
}
