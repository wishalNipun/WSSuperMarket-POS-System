package lk.ijse.pos.dto;

public class ReportDTO {
    private String date1;
    private String date2;
    private Double total;

    public ReportDTO(String date1, Double total) {
        this.date1 = date1;
        this.total = total;
    }

    public ReportDTO(String date1, String date2, Double total) {
        this.date1 = date1;
        this.date2 = date2;
        this.total = total;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ReportDTO{" +
                "date1='" + date1 + '\'' +
                ", date2='" + date2 + '\'' +
                ", total=" + total +
                '}';
    }
}
