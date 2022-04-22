package com.example.myapplication.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import cn.pedant.SweetAlert.SweetAlertDialog;
import java.util.List;

public class AdapterSaleEditable extends FirebaseRecyclerAdapter<ModelSale, AdapterSaleEditable.ViewHolder> {
    private Context context;
    int salesquant;
    private List<ModelSale> modelSaleList;
    private boolean discountFlag = false;
    private boolean priceEvenFlag = false;
    private Activity activity;
    private String sellID,prodName;
    DatabaseReference FromPath, ToPath, reference1;
    FirebaseUser user;
    public AdapterSaleEditable(List<ModelSale> modelSales,@NonNull FirebaseRecyclerOptions<ModelSale> options) {
        super(options);
        this.modelSaleList = modelSales;

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

        sellID = getRef(position).getKey();

        holder.btnUpdate.setOnClickListener(v->updateSaleRecord(holder,holder.getAdapterPosition()));
        holder.btnCancelUpdate.setOnClickListener(v->remove());
        holder.registerTextWatchers();
        //holder.sumColumn();
    }

    private void updateSaleRecord(ViewHolder holder, int position)
    {
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        reference1 = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Sales").child(sellID);
        FromPath = FirebaseDatabase.getInstance().getReference("users").child(uid).child("Sales");
        ToPath = FirebaseDatabase.getInstance().getReference("users").child(uid).child("allsales");
        holder.btnUpdate.setEnabled(false);
        int quantity = 1;
        int discount = 0;
        int price = 0;
        int total = 0;
        String strQuantity = holder.inputSaleQuantity.getText().toString().trim();
        String strDiscount = holder.inputSaleDiscount.getText().toString().trim();
        String strPrice = holder.inputSalePrice.getText().toString().trim();
        String strTotal = holder.inputTotalPrice.getText().toString().trim();

        if (strQuantity.isEmpty())
        {
            holder.inputSaleQuantity.setError("Enter Sale Quantity");
            holder.inputSaleQuantity.requestFocus();
            holder.btnUpdate.setEnabled(true);
            return;
        }
        else
            quantity = Integer.parseInt(strQuantity);
        salesquant = quantity;

        if (!strPrice.isEmpty())
            price = Integer.parseInt(strPrice);

        if (!strDiscount.isEmpty())
            discount = Integer.parseInt(strDiscount);

        if (!strTotal.isEmpty())
            total = Integer.parseInt(strTotal);

        reference1.child("saleQuantity").setValue(quantity);
        reference1.child("salePrice").setValue(price);
        reference1.child("saleDiscount").setValue(discount);
        reference1.child("productTotalPrice").setValue(total);
        reduceAmount();
        moveRecord(FromPath, ToPath);
    }




    private void remove()
    {
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase.getInstance().getReference("users").child(uid).child("Sales").removeValue();
    }

    private void moveRecord(DatabaseReference fromPath, final DatabaseReference toPath) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.setValue(dataSnapshot.getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()) {
                            Log.d("TAG", "Success!");
                        } else {
                            Log.d("TAG", "Copy failed!");
                        }
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG", databaseError.getMessage()); //Never ignore potential errors!
            }
        };
        fromPath.addListenerForSingleValueEvent(valueEventListener);
    }

    private void reduceAmount() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase.getInstance().getReference("users").child(uid).child("Sales").child(sellID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String productid = snapshot.child("productId").getValue().toString();

                FirebaseDatabase.getInstance().getReference("users").child(uid).child("Products").child(productid)
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        long productquantity = (long) snapshot.child("productQuantity").getValue();
                        long finalquantity = productquantity - salesquant;
                        FirebaseDatabase.getInstance().getReference("users").child(uid).
                                child("Products").child(productid).child("productQuantity").setValue(finalquantity);


                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public void alertLowQuantity(ViewHolder holder) {
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();

        FirebaseDatabase.getInstance().getReference("users").child(uid).child("Sales").child(sellID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String productName = snapshot.child("productName").getValue().toString();
                int productQuantity = Integer.parseInt(snapshot.child("productQuantity").getValue().toString()+1);
                SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
                sweetAlertDialog.setTitleText("The Available Quantity Of "+productName+" is "+productQuantity+" . Please Decrease The Quantity.");
                sweetAlertDialog.show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTotalPrice, tvSalePrice, tvProductName, tvProductSize, tvProductCategory, tvSaleTime, tvProductPrice, tvProductBrand, tvProductManufacture, tvProductExpire, tvCount;
        private EditText inputSaleQuantity, inputSaleDiscount, inputSalePrice, inputTotalPrice;
        private CardView cvSell;
        private int position;
        private TextWatcher quantityWatcher;
        private TextWatcher priceWatcher;
        private TextWatcher discountWatcher;
        private Button btnCancelUpdate, btnUpdate;

        private void registerTextWatchers() {
            Log.d("Pharmical", "Registring Listener");
            inputSaleQuantity.addTextChangedListener(quantityWatcher);
            inputSalePrice.addTextChangedListener(priceWatcher);
            inputSaleDiscount.addTextChangedListener(discountWatcher);
        }

        private void unregisterTextWatcher() {
            Log.d("Pharmical", "UnRegistering Listener");
            inputSaleQuantity.removeTextChangedListener(quantityWatcher);
            inputSalePrice.removeTextChangedListener(priceWatcher);
            inputSaleDiscount.removeTextChangedListener(discountWatcher);
        }

        public void sumColumn()
        {
            user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            FirebaseDatabase.getInstance().getReference("users").child(uid).child("Sales").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long salePrice = 0;
                    long totalPrice = 0;
                    for( DataSnapshot ds :snapshot.getChildren()) {
                        long saleprice = ds.child("salePrice").getValue(Long.class);
                        long totalprice = ds.child("productTotalPrice").getValue(Long.class);
                        salePrice = salePrice + saleprice;

                        totalPrice = totalPrice + totalprice;
                    }
                    //updateTotalValue(totalPrice,salePrice);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        public void updateTotalValue(long totalPrice, long salePrice) {
            tvTotalPrice.setText(String.valueOf(totalPrice));
            tvSalePrice.setText(String.valueOf(salePrice));
        }

        private void priceEvent() {
            Log.d("Pharmical", "PriceEvent Method Called");
            unregisterTextWatcher();
            int totalPrice = Integer.parseInt(inputTotalPrice.getText().toString().trim());
            String sellPriceString = inputSalePrice.getText().toString().trim();
            if (sellPriceString.trim().length() > 0) {
                int sellPrice = Integer.parseInt(inputSalePrice.getText().toString().trim());
                int discount = percentage(sellPrice, totalPrice);
                inputSaleDiscount.setText(String.valueOf(discount));
                //sumColumn();
            } else {
                inputSaleDiscount.setText(String.valueOf(100));
            }
            registerTextWatchers();
        }

        private void quantityEvent() {
            Log.d("Pharmical", "quantityEvent Method Called");
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
            int salePrice = percentageDec(finalPrice, percentage);
            inputSalePrice.setText(String.valueOf(salePrice));
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

            position = getAdapterPosition();

            quantityWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    showActionButton();
                    Log.d("Pharmical", "afterTextChanged: quantityWatcher Event Listener Called");
                    quantityEvent();
                }
            };

            priceWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    Log.d("Pharmical", "afterTextChanged: priceWatcher Event Listener Called");
                    showActionButton();
                    priceEvent();
                }
            };

            discountWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    Log.d("Pharmical", "afterTextChanged: discountWatcher Event Listener Called");
                    showActionButton();
                    discountInputEvent();
                }
            };

            registerTextWatchers();

        }

    }
}
