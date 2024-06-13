package com.example.createpdfjava;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_ID_MULTIPLE_PERMISSIONS = 1;
    Button btnGeneratePDf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGeneratePDf = findViewById(R.id.btnGeneratePDf);
        btnGeneratePDf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createPdf();
                } catch (DocumentException | IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            checkAndRequestPermissions();
        }


    }

    private void createPdf() throws DocumentException, IOException {
        Font headerBold = new Font(Font.FontFamily.TIMES_ROMAN, 11,
                Font.BOLD, BaseColor.WHITE);
        Font small_bold = new Font(Font.FontFamily.TIMES_ROMAN, 8,
                Font.BOLD);
        Font small_bold_for_name = new Font(Font.FontFamily.TIMES_ROMAN,
                10, Font.BOLD);
        Font small_bold1 = new Font(Font.FontFamily.TIMES_ROMAN, 6,
                Font.BOLD);

        Font small_normal = new Font(Font.FontFamily.TIMES_ROMAN, 8,
                Font.NORMAL);


        File mypath = createFile(this, "11111" + "P01.pdf", false);


        PdfPCell cell;

        Rectangle rect = new Rectangle(594f, 792f);

        Document document = new Document(rect, 50, 50, 50, 50);
        // Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter pdf_writer = null;
        pdf_writer = PdfWriter.getInstance(document, new FileOutputStream(mypath.getAbsolutePath()));

        document.open();

        // For SBI- Life Logo starts
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.kalash);

        bitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
        Image img_sbi_logo = Image.getInstance(stream.toByteArray());
        img_sbi_logo.setAlignment(Image.LEFT);
        img_sbi_logo.getSpacingAfter();
        img_sbi_logo.scaleToFit(80, 50);

        Paragraph para_img_logo = new Paragraph("");
        para_img_logo.add(img_sbi_logo);

        Paragraph para_img_logo_after_space_1 = new Paragraph(" ");

        document.add(para_img_logo);
        // For SBI- Life Logo ends

        // To draw line after the sbi logo image
        document.add(new LineSeparator());
        document.add(para_img_logo_after_space_1);

        // For the BI Smart Elite Table Header(Grey One)
        Paragraph Para_Header = new Paragraph();
        Para_Header
                .add(new Paragraph(
                        "Premium Quotation for SBI LIFE - Rinn Raksha (UIN: 111N078V03)",
                        headerBold));

        PdfPTable headertable = new PdfPTable(1);
        headertable.setWidthPercentage(100);
        PdfPCell c1 = new PdfPCell(new Phrase(Para_Header));
        c1.setBackgroundColor(BaseColor.DARK_GRAY);
        c1.setPadding(5);
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        headertable.addCell(c1);
        headertable.setHorizontalAlignment(Element.ALIGN_CENTER);

        Paragraph para_address = new Paragraph(
                "SBI Life Insurance Co. Ltd" + "\n" + "Registered & Corporate Office: 'Natraj', M.V. Road and Western Express, Highway Junction, Andheri (East),Mumbai  400069. IRDAI Registration  No. 111",
                small_bold_for_name);
        para_address.setAlignment(Element.ALIGN_CENTER);
        Paragraph para_address1 = new Paragraph(
                "Website: www.sbilife.co.in | Email: info@sbilife.co.in | CIN: L99999MH2000PLC129113. Toll Free: 1800 267 9090 (Between 9.00 am & 9.00 pm)",
                small_bold);
        para_address1.setAlignment(Element.ALIGN_CENTER);
        document.add(para_address);
        document.add(para_address1);
        document.add(para_img_logo_after_space_1);
        document.add(headertable);
        document.add(para_img_logo_after_space_1);
        document.add(para_img_logo_after_space_1);

        PdfPTable table_proposer_name = new PdfPTable(4);
        // float[] columnWidths_table_proposer_name = { 2f, 2f, 1f, 2f };
        // table_proposer_name.setWidths(columnWidths_table_proposer_name);
        table_proposer_name.setWidthPercentage(100);

        PdfPCell ProposalNumber_cell_1 = new PdfPCell(new Paragraph(
                "Quotation Number", small_normal));
        PdfPCell ProposalNumber_cell_2 = new PdfPCell(new Paragraph(
                "1234567890", small_bold1)); // value
        ProposalNumber_cell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell NameofProposal_cell_3 = new PdfPCell(new Paragraph(
                "Proposer Name ", small_normal));
        PdfPCell NameofProposal_cell_4 = new PdfPCell(new Paragraph(
                "xyz", small_bold1)); //value
        NameofProposal_cell_4.setHorizontalAlignment(Element.ALIGN_CENTER);

        NameofProposal_cell_4.setVerticalAlignment(Element.ALIGN_CENTER);

        ProposalNumber_cell_1.setPadding(5);
        ProposalNumber_cell_2.setPadding(5);
        NameofProposal_cell_3.setPadding(5);
        NameofProposal_cell_4.setPadding(5);

        table_proposer_name.addCell(ProposalNumber_cell_1);
        table_proposer_name.addCell(ProposalNumber_cell_2);
        table_proposer_name.addCell(NameofProposal_cell_3);
        table_proposer_name.addCell(NameofProposal_cell_4);
        document.add(table_proposer_name);
        document.add(para_img_logo_after_space_1);
        PdfPTable loantype_table = new PdfPTable(4);
        loantype_table.setWidths(new float[]{5f, 5f, 5f, 5f});
        loantype_table.setWidthPercentage(100f);

        loantype_table.setHorizontalAlignment(Element.ALIGN_LEFT);

        // 1st row
        cell = new PdfPCell(new Phrase("Home Loan", small_bold)); //value
        cell.setColspan(4);
        cell.setPadding(5);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT
                | Rectangle.RIGHT | Rectangle.TOP);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        loantype_table.addCell(cell);

        // //2nd row
        // cell = new PdfPCell(new Phrase("Name",small_normal));
        // cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        // cell.setPadding(5);
        // Loandetails.addCell(cell);

        cell = new PdfPCell(new Phrase("Loan Type", small_normal));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPadding(5);
        loantype_table.addCell(cell);

        cell = new PdfPCell(new Phrase("Home Loan" + "", small_normal));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPadding(5);
        loantype_table.addCell(cell);


        cell = new PdfPCell(new Phrase("Staff/Non-Staff ", small_normal));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPadding(5);
        loantype_table.addCell(cell);
        cell = new PdfPCell(new Phrase("Staff", small_normal));//value
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPadding(5);
        loantype_table.addCell(cell);
        document.add(loantype_table);
        document.add(para_img_logo_after_space_1);
        document.add(para_img_logo_after_space_1);


        PdfPTable finaltable = new PdfPTable(2);
        finaltable.setWidths(new float[]{6f, 6f});
        finaltable.setWidthPercentage(100f);

        finaltable.setHorizontalAlignment(Element.ALIGN_LEFT);

        // 1st row
        cell = new PdfPCell(new Phrase("Premium Installment",
                small_bold));
        cell.setColspan(2);
        cell.setPadding(5);
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setBorder(Rectangle.BOTTOM | Rectangle.LEFT
                | Rectangle.RIGHT | Rectangle.TOP);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        finaltable.addCell(cell);

        // //2nd row
        // cell = new PdfPCell(new Phrase("Name",small_normal));
        // cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        // cell.setPadding(5);
        // Loandetails.addCell(cell);

        cell = new PdfPCell(
                new Phrase(
                        "Premium inclusive of Applicable Tax payable for all borrowers (Rs.)",
                        small_normal));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPadding(5);
        finaltable.addCell(cell);

        cell = new PdfPCell(new Phrase("37247", // value
                small_normal));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPadding(5);
        finaltable.addCell(cell);

        cell = new PdfPCell(new Phrase("Applicable Tax (Rs.)", small_normal));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPadding(5);
        finaltable.addCell(cell);

        cell = new PdfPCell(new Phrase("3826484", // value
                small_normal));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPadding(5);
        finaltable.addCell(cell);

        cell = new PdfPCell(
                new Phrase(
                        "Premium inclusive of Applicable Tax payable for all borrowers (Rs.)",
                        small_normal));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPadding(5);
        finaltable.addCell(cell);

        cell = new PdfPCell(new Phrase("82648326", // value
                small_normal));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPadding(5);
        finaltable.addCell(cell);
        document.add(finaltable);

        document.add(para_img_logo_after_space_1);
        document.add(para_img_logo_after_space_1);

        Font sub_headerBold = new Font(Font.FontFamily.TIMES_ROMAN, 7, Font.BOLD);
        Font small_normal2 = new Font(Font.FontFamily.TIMES_ROMAN, 6,
                Font.NORMAL);
        Paragraph Para_health_Header = new Paragraph();
        Para_health_Header
                .add(new Paragraph("5. MEDICAL QUESTIONNAIRE:- In case where insurance is proposed on Minor Life, the answers should relate to medical status of Minor Life to be Assured",
                        sub_headerBold));

        PdfPTable health_headertable = new PdfPTable(1);
        health_headertable.setSpacingBefore(4f);
        health_headertable.setWidthPercentage(100);
        PdfPCell health_c1 = new PdfPCell(new Phrase(Para_health_Header));
        health_c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
        health_c1.setPadding(5);
        health_c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        health_headertable.addCell(health_c1);
        health_headertable.setHorizontalAlignment(Element.ALIGN_CENTER);
        document.add(health_headertable);

        PdfPTable row_tag = new PdfPTable(1);
        row_tag.setWidthPercentage(100);
        PdfPCell tag_cell = new PdfPCell(new Paragraph("Important :Please read this section fully and give correct details.", small_normal2));
        tag_cell.setPadding(5);
        row_tag.addCell(tag_cell);
        document.add(row_tag);

        // if (productCode.Equals(BLC.ProductString.sbi_life_rinn_raksha))
        // {
        String height_pdf = (new DecimalFormat("##.##")).format(Double
                .parseDouble("0.0".trim()));
        PdfPTable row1 = new PdfPTable(4);
        row1.setWidthPercentage(100);
        PdfPCell row1_cell_1 = new PdfPCell(new Paragraph(
                "Height", small_normal));
        // String height_pdf = "";
        PdfPCell row1_cell_2 = new PdfPCell(new Paragraph(height_pdf
                + " Cms", small_bold));

        row1_cell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        row1_cell_2.setVerticalAlignment(Element.ALIGN_CENTER);
        PdfPCell row1_cell_3 = new PdfPCell(new Paragraph("Weight",
                small_normal));

        PdfPCell row1_cell_4 = new PdfPCell(new Paragraph("v"
                + " Kgs", small_bold));

        row1_cell_4.setHorizontalAlignment(Element.ALIGN_CENTER);
        row1_cell_4.setVerticalAlignment(Element.ALIGN_CENTER);
        row1_cell_1.setPadding(5);
        row1_cell_2.setPadding(5);
        row1_cell_3.setPadding(5);
        row1_cell_4.setPadding(5);

        row1.addCell(row1_cell_1);
        row1.addCell(row1_cell_2);
        row1.addCell(row1_cell_3);
        row1.addCell(row1_cell_4);
        document.add(row1);

        PdfPTable mh_row1 = new PdfPTable(2);
        mh_row1.setWidthPercentage(100);
        PdfPCell mh_row1_cell_1 = new PdfPCell(
                new Paragraph(
                        "i. Have you consulted any doctor for surgical operations or have been hospitalised for any disorder other than minor cough,cold or flu during the last 5 years?",
                        small_normal));
        mh_row1_cell_1.setPadding(5);
        PdfPCell mh_row1_cell_2 = new PdfPCell(new Paragraph(
                "v", small_bold));
        mh_row1_cell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        mh_row1_cell_2.setVerticalAlignment(Element.ALIGN_CENTER);
        mh_row1_cell_2.setPadding(5);
        mh_row1.setWidths(new float[]{0.8f, 0.2f});
        mh_row1.addCell(mh_row1_cell_1);
        mh_row1.addCell(mh_row1_cell_2);
        document.add(mh_row1);
        // }
        // medical history row2_1

        PdfPTable mh_row2_1 = new PdfPTable(2);
        mh_row2_1.setWidthPercentage(100);
        PdfPCell mh_row2_1_cell_1 = new PdfPCell(
                new Paragraph(
                        "ii. Have you ever had any illness/injury, major surgical operation or received any treatment for any medical condition for a continuous period of more than 14 days? (Except for minor cough, cold, flu, appendicitis & typhoid)",
                        small_normal));
        mh_row2_1_cell_1.setPadding(5);
        PdfPCell mh_row2_1_cell_2 = new PdfPCell(new Paragraph(
                "v", small_bold));
        mh_row2_1_cell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        mh_row2_1_cell_2.setVerticalAlignment(Element.ALIGN_CENTER);
        mh_row2_1_cell_2.setPadding(5);
        mh_row2_1.setWidths(new float[]{0.8f, 0.2f});
        mh_row2_1.addCell(mh_row2_1_cell_1);
        mh_row2_1.addCell(mh_row2_1_cell_2);
        document.add(mh_row2_1);

        // medical history row3
        PdfPTable mh_row3 = new PdfPTable(1);
        mh_row3.setWidthPercentage(100);
        PdfPCell mh_row3_cell_1 = new PdfPCell(
                new Paragraph(
                        "iii. Have you ever suffered from / been treated / hospitalized for or diagnosed to have -",
                        small_normal));
        mh_row3_cell_1.setPadding(5);
        mh_row3.addCell(mh_row3_cell_1);
        document.add(mh_row3);
        // medical history row4

        PdfPTable mh_row4 = new PdfPTable(4);
        mh_row4.setWidthPercentage(100);

        PdfPCell mh_row4_cell_1 = new PdfPCell(new Paragraph(
                "(a) Diabetes, raised blood sugar or high blood pressure",
                small_normal));
        mh_row4_cell_1.setPadding(5);

        PdfPCell mh_row4_cell_2 = new PdfPCell(new Paragraph(
                "v", small_bold));
        mh_row4_cell_2.setPadding(5);
        mh_row4_cell_2.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell mh_row4_cell_3 = new PdfPCell(
                new Paragraph(
                        "(b) Chest pain, heart attack, heart disease or any other disorder of the circulatory system.",
                        small_normal));
        mh_row4_cell_3.setPadding(5);

        PdfPCell mh_row4_cell_4 = new PdfPCell(new Paragraph(
                "v", small_bold));
        mh_row4_cell_4.setPadding(5);
        mh_row4_cell_4.setHorizontalAlignment(Element.ALIGN_CENTER);

        mh_row4.addCell(mh_row4_cell_1);
        mh_row4.addCell(mh_row4_cell_2);
        mh_row4.addCell(mh_row4_cell_3);
        mh_row4.addCell(mh_row4_cell_4);
        document.add(mh_row4);

        // medical history row5

        PdfPTable mh_row5 = new PdfPTable(4);
        mh_row5.setWidthPercentage(100);
        PdfPCell mh_row5_cell_1 = new PdfPCell(
                new Paragraph(
                        "(c) Stroke, paralysis, disorder of the brain/nervous system.",
                        small_normal));
        mh_row5_cell_1.setPadding(5);
        PdfPCell mh_row5_cell_2 = new PdfPCell(new Paragraph(
                "value", small_bold));
        mh_row5_cell_2.setPadding(5);
        mh_row5_cell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell mh_row5_cell_3 = new PdfPCell(new Paragraph(
                "(d) HIV infection, AIDS", small_normal));
        mh_row5_cell_3.setPadding(5);
        PdfPCell mh_row5_cell_4 = new PdfPCell(new Paragraph(
                "value", small_bold));
        mh_row5_cell_4.setPadding(5);
        mh_row5_cell_4.setHorizontalAlignment(Element.ALIGN_CENTER);
        mh_row5.addCell(mh_row5_cell_1);
        mh_row5.addCell(mh_row5_cell_2);
        mh_row5.addCell(mh_row5_cell_3);
        mh_row5.addCell(mh_row5_cell_4);
        document.add(mh_row5);

        // medical history row6
        PdfPTable mh_row6 = new PdfPTable(4);
        mh_row6.setWidthPercentage(100);
        PdfPCell mh_row6_cell_1 = new PdfPCell(new Paragraph(
                "(e) Cancer, tumor, growth or cyst of any kind",
                small_normal));
        mh_row6_cell_1.setPadding(5);
        PdfPCell mh_row6_cell_2 = new PdfPCell(new Paragraph(
                "value", small_bold));
        mh_row6_cell_2.setPadding(5);
        mh_row6_cell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell mh_row6_cell_3 = new PdfPCell(
                new Paragraph(
                        "(f) Any genitourinary or kidney disorder, Hepatitis B/C or any other liver disease",
                        small_normal));
        mh_row6_cell_3.setPadding(5);
        PdfPCell mh_row6_cell_4 = new PdfPCell(new Paragraph(
                "value", small_bold));
        mh_row6_cell_4.setPadding(5);
        mh_row6_cell_4.setHorizontalAlignment(Element.ALIGN_CENTER);
        mh_row6.addCell(mh_row6_cell_1);
        mh_row6.addCell(mh_row6_cell_2);
        mh_row6.addCell(mh_row6_cell_3);
        mh_row6.addCell(mh_row6_cell_4);
        document.add(mh_row6);

        // medical history row7
        PdfPTable mh_row7 = new PdfPTable(2);
        mh_row7.setWidthPercentage(100);
        PdfPCell mh_row7_cell_1 = new PdfPCell(
                new Paragraph(
                        "(g) Any digestive disorder (ulcer, colitis etc), any disease of the gall bladder, spleen, any blood disorder, disorder of any other gland (e.g. Thyroid etc) or any musculoskeletal disorder",
                        small_normal));
        mh_row7_cell_1.setPadding(5);
        PdfPCell mh_row7_cell_2 = new PdfPCell(new Paragraph(
                "value", small_bold));
        mh_row7_cell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        mh_row7_cell_2.setVerticalAlignment(Element.ALIGN_CENTER);
        mh_row7_cell_2.setPadding(5);
        mh_row7.setWidths(new float[]{0.8f, 0.2f});
        mh_row7.addCell(mh_row7_cell_1);
        mh_row7.addCell(mh_row7_cell_2);
        document.add(mh_row7);

        // medical history row8

        PdfPTable mh_row8 = new PdfPTable(4);
        mh_row8.setWidthPercentage(100);
        PdfPCell mh_row8_cell_1 = new PdfPCell(
                new Paragraph(
                        "(h) Asthma, Tuberculosis, Pneumonia, or any other disease of the lung.",
                        small_normal));
        mh_row8_cell_1.setPadding(5);
        PdfPCell mh_row8_cell_2 = new PdfPCell(new Paragraph(
                "value", small_bold));
        mh_row8_cell_2.setPadding(5);
        mh_row8_cell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        PdfPCell mh_row8_cell_3 = new PdfPCell(
                new Paragraph(
                        "(i) Mental, psychiatric or nervous disorder",
                        small_normal));
        mh_row8_cell_3.setPadding(5);
        PdfPCell mh_row8_cell_4 = new PdfPCell(new Paragraph(
                "value", small_bold));
        mh_row8_cell_4.setPadding(5);
        mh_row8_cell_4.setHorizontalAlignment(Element.ALIGN_CENTER);
        mh_row8.addCell(mh_row8_cell_1);
        mh_row8.addCell(mh_row8_cell_2);
        mh_row8.addCell(mh_row8_cell_3);
        mh_row8.addCell(mh_row8_cell_4);
        document.add(mh_row8);

        PdfPTable mh_row9 = new PdfPTable(2);
        mh_row9.setWidthPercentage(100);
        PdfPCell mh_row9_cell_1 = new PdfPCell(
                new Paragraph(
                        "(iv) Have you suffered from any other disease not mentioned above?",
                        small_normal));
        mh_row9_cell_1.setPadding(5);
        PdfPCell mh_row9_cell_2 = new PdfPCell(new Paragraph(
                "value", small_bold));
        mh_row9_cell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        mh_row9_cell_2.setVerticalAlignment(Element.ALIGN_CENTER);
        mh_row9_cell_2.setPadding(5);
        mh_row9.setWidths(new float[]{0.8f, 0.2f});
        mh_row9.addCell(mh_row9_cell_1);
        mh_row9.addCell(mh_row9_cell_2);
        document.add(mh_row9);

        PdfPTable mh_row10 = new PdfPTable(2);
        mh_row10.setWidthPercentage(100);
        PdfPCell mh_row10_cell_1 = new PdfPCell(
                new Paragraph(
                        "(v) Are you at present taking any medication, or on any special diet or on any treatment?",
                        small_normal));
        mh_row10_cell_1.setPadding(5);
        PdfPCell mh_row10_cell_2 = new PdfPCell(new Paragraph(
                "value", small_bold));
        mh_row10_cell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        mh_row10_cell_2.setVerticalAlignment(Element.ALIGN_CENTER);
        mh_row10_cell_2.setPadding(5);
        mh_row10.setWidths(new float[]{0.8f, 0.2f});
        mh_row10.addCell(mh_row10_cell_1);
        mh_row10.addCell(mh_row10_cell_2);
        document.add(mh_row10);

        PdfPTable mh_row11 = new PdfPTable(2);
        mh_row11.setWidthPercentage(100);
        PdfPCell mh_row11_cell_1 = new PdfPCell(
                new Paragraph(
                        "(vi) Has a proposal for Life Insurance, ever been declined, postponed, withdrawn or accepted at extra premium?",
                        small_normal));
        mh_row11_cell_1.setPadding(5);
        PdfPCell mh_row11_cell_2 = new PdfPCell(new Paragraph(
                "value", small_bold));
        mh_row11_cell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        mh_row11_cell_2.setVerticalAlignment(Element.ALIGN_CENTER);
        mh_row11_cell_2.setPadding(5);
        mh_row11.setWidths(new float[]{0.8f, 0.2f});
        mh_row11.addCell(mh_row11_cell_1);
        mh_row11.addCell(mh_row11_cell_2);
        document.add(mh_row11);

        PdfPTable mh_row12 = new PdfPTable(2);
        mh_row12.setWidthPercentage(100);
        PdfPCell mh_row12_cell_1 = new PdfPCell(
                new Paragraph(
                        "(vii) Have you had or have been advised to undergo any of the following tests or investigations?",
                        small_normal));
        mh_row12_cell_1.setPadding(5);
        PdfPCell mh_row12_cell_2 = new PdfPCell(new Paragraph(
                "value", small_bold));
        mh_row12_cell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
        mh_row12_cell_2.setVerticalAlignment(Element.ALIGN_CENTER);
        mh_row12_cell_2.setPadding(5);
        mh_row12.setWidths(new float[]{0.8f, 0.2f});
        mh_row12.addCell(mh_row12_cell_1);
        mh_row12.addCell(mh_row12_cell_2);
        document.add(mh_row12);

        PdfPTable Advc_test_ivstgtn_rinnraksha = new PdfPTable(2);
        Advc_test_ivstgtn_rinnraksha.setWidthPercentage(100);
        PdfPCell Advc_test_ivstgtn_rinnraksha_cell1 = new PdfPCell(
                new Paragraph(
                        "(IF Yes, Please Select which of the following and provide reasons for undergoing the tests) Ultra Sonography,CT Scan/MRI,Biopsy,Coronary Angiography ",
                        small_normal));
        String str_test = "NA";
        PdfPCell Advc_test_ivstgtn_rinnraksha_cell2 = new PdfPCell(
                new Paragraph(str_test, small_bold));
        Advc_test_ivstgtn_rinnraksha_cell1.setPadding(5);
        Advc_test_ivstgtn_rinnraksha_cell2.setPadding(5);
        Advc_test_ivstgtn_rinnraksha_cell2
                .setHorizontalAlignment(Element.ALIGN_CENTER);
        Advc_test_ivstgtn_rinnraksha
                .addCell(Advc_test_ivstgtn_rinnraksha_cell1);
        Advc_test_ivstgtn_rinnraksha
                .addCell(Advc_test_ivstgtn_rinnraksha_cell2);
        document.add(Advc_test_ivstgtn_rinnraksha);

        PdfPTable BI_Pdftable_proposer_signature211 = new PdfPTable(3);
        BI_Pdftable_proposer_signature211.setWidthPercentage(100);
        BI_Pdftable_proposer_signature211.setSpacingBefore(4f);
        PdfPCell BI_Pdftable_proposer_signature2_cell111 = new PdfPCell(
                new Paragraph("Signature of Member or Proposer(In case Member is a Minor)" + "\n"
                        + "This document is eSigned by "
                        + "xyz",
                        small_normal));
        BI_Pdftable_proposer_signature2_cell111
                .setHorizontalAlignment(Element.ALIGN_CENTER);
        BI_Pdftable_proposer_signature2_cell111
                .setVerticalAlignment(Element.ALIGN_CENTER);
        PdfPCell BI_Pdftable_proposer_signature2_cell211 = new PdfPCell();
        BI_Pdftable_proposer_signature2_cell211.setFixedHeight(60f);

        // added by me
        PdfPCell BI_Pdftable_proposer_signature2_cell311 = new PdfPCell(
                new Paragraph("", small_normal));

        BI_Pdftable_proposer_signature2_cell311
                .setHorizontalAlignment(Element.ALIGN_CENTER);
        BI_Pdftable_proposer_signature2_cell311
                .setVerticalAlignment(Element.ALIGN_CENTER);

        // end added by me
        String str_sign_of_proposer="";
        Image image_terms_condition_Proposer211 = null;
        if (!str_sign_of_proposer.equals("")
                && (str_sign_of_proposer != null)) {

            byte[] fbyt_TCProposerSign11 = Base64.decode(
                    str_sign_of_proposer, 0);
            Bitmap Proposerbitmap11 = BitmapFactory.decodeByteArray(
                    fbyt_TCProposerSign11, 0, fbyt_TCProposerSign11.length);

            ByteArrayOutputStream temrs_conditons_Proposer_stream11 = new ByteArrayOutputStream();

            (Proposerbitmap11).compress(Bitmap.CompressFormat.PNG, 50,
                    temrs_conditons_Proposer_stream11);
            image_terms_condition_Proposer211 = Image
                    .getInstance(temrs_conditons_Proposer_stream11
                            .toByteArray());

            BI_Pdftable_proposer_signature2_cell211
                    .setImage(image_terms_condition_Proposer211);
        }
        BI_Pdftable_proposer_signature211
                .addCell(BI_Pdftable_proposer_signature2_cell111);
        BI_Pdftable_proposer_signature211
                .addCell(BI_Pdftable_proposer_signature2_cell211);

        // added by me
        String photo="";
        Image photoImage11 = null;
        if (!photo.equals("") && photo != null) {

            ByteArrayOutputStream Photo_stream11 = new ByteArrayOutputStream();
            byte[] fbyt_Proposer_photo11 = Base64.decode(photo, 0);
            Bitmap Cr_sr_signbitmap11 = BitmapFactory.decodeByteArray(
                    fbyt_Proposer_photo11, 0, fbyt_Proposer_photo11.length);
            (Cr_sr_signbitmap11).compress(Bitmap.CompressFormat.JPEG, 50,
                    Photo_stream11);
            photoImage11 = Image.getInstance(Photo_stream11.toByteArray());
            /*
             * photoImage11.setAlignment(Image.RIGHT);
             * photoImage11.scaleToFit(200, 90); document.add(photoImage11);
             */
            BI_Pdftable_proposer_signature2_cell311.setImage(photoImage11);

        }

        BI_Pdftable_proposer_signature211
                .addCell(BI_Pdftable_proposer_signature2_cell311);
        // end addded by me

        document.add(BI_Pdftable_proposer_signature211);

        document.close();
        Toast.makeText(this, "PDF Generated", Toast.LENGTH_SHORT).show();
    }

    public File createFile(Context context, String fileName, boolean isDelete) {


        Log.i("test", fileName);

        File directory = new File(Environment.getExternalStorageDirectory().getPath() + fileName);

        File file = new File(directory.getPath(), fileName);


        if (file.exists()) {
            return file;
        } else {


            Log.i("test", fileName);
            String folderName = "/ConnectPDf/";
            // Make a new directory/folder
            directory = new File(getFilePath(context) + folderName);

            if (!directory.exists()) {
                directory.mkdir();
            }
            // make a new text file in that created new directory/folder
//            File file = new File(directory.getPath(), "PaymentBank_Logs.txt");
            file = new File(directory.getPath(), fileName);


//            if (!file.exists() && directory.exists()) {
//                try {
//                    file.createNewFile();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }

            if (isDelete && file.exists()) {
                file.delete();
            }

            return file;

        }


    }

    public static File getFilePath(Context context) {
        File documentDirectory = null;

        ContextWrapper contextWrapper = new ContextWrapper(context);

        documentDirectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);


        return documentDirectory;
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    public boolean checkAndRequestPermissions() {

        int devicePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES);

        List<String> listPermissionsNeeded = new ArrayList<>();

        if (devicePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_MEDIA_IMAGES);
        }

        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_ID_MULTIPLE_PERMISSIONS) {
            Map<String, Integer> perms = new HashMap<>();
            // Initialize the map with both permissions
            perms.put(Manifest.permission.READ_MEDIA_AUDIO, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.READ_MEDIA_VIDEO, PackageManager.PERMISSION_GRANTED);
            perms.put(Manifest.permission.READ_MEDIA_IMAGES, PackageManager.PERMISSION_GRANTED);
            // Fill with actual results from user
            if (grantResults.length > 0) {
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);

                // Check for both permissions
                if (perms.get(Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                    // process the normal flow
                    //else any one or both the permissions are not granted
                    Toast.makeText(this, "Permissions Granted :", Toast.LENGTH_LONG).show();

                } else {
                    //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
                    // shouldShowRequestPermissionRationale will return true
                    //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_AUDIO)
                            || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_VIDEO)
                            || ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_IMAGES)) {
                        Toast.makeText(this, "Permissions Granted", Toast.LENGTH_LONG).show();
                    }
                    //permission is denied (and never ask again is  checked)
                    //shouldShowRequestPermissionRationale will return false
                    else {
                        // Toast.makeText(this, "Go to settings and enable permissions", Toast.LENGTH_LONG)
                        //      .show();
                        //                            //proceed with logic by disabling the related features or quit the app.
                    }
                }
            }
        }

    }


}