
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Separation{
    private String string;
    private String name;
    private String phonenum;
    private String leve;
    private Province province=new Province();
    private City city=new City();
    private Area area=new Area();
    private Street street;
    private List<String> addressList=new ArrayList<>();


    public   Separation(String input){
        string=input.substring(0,input.length()-1);
    }
    public void sepLeveAndName(){
        String[] str=string.split("[!,]");
        leve=str[0];
        name=str[1];
        string=str[2];
    }
    private void sepPhonenum() {
        Pattern pattern = Pattern.compile("[\\d]{11}");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            phonenum = matcher.group(0);
            String[] str = string.split(phonenum);
            string = string.replace(phonenum, "");
        }
    }
    private void sepProvince() {
        if (DataBase.getProvinces() != null) {
            String str = string.substring(0, 2);
            for (Province province : DataBase.getProvinces()) {
                if (province.getName().contains(str)) {
                    if (province.getName().equals("北京") ||
                            province.getName().equals("天津") ||
                            province.getName().equals("上海") ||
                            province.getName().equals("重庆")) {
                        string = province.getName() + string;
                    }
                    this.province = province;
                    int len = province.getName().length();
                    for (int i = 0; i < len; i++) {
                        if (string.charAt(i) != province.getName().charAt(i)) {
                            len = i;
                            break;
                        }
                    }
                    string = string.substring(len);
                    break;
                }
            }
        }
    }
    private void sepCity(){
        String str=string.substring(0,2);
        for(City city:this.province.getCities()){
            if(city.getName().contains(str)){
                this.city=city;
                int len=city.getName().length();
                for(int i=0;i<len;i++)
                {
                    if(string.charAt(i)!=city.getName().charAt(i))
                    {
                        len=i;
                        break;
                    }
                }
                string=string.substring(len);
            }
        }
    }
    private void sepArea(){
        String str=string.substring(0,2);
        if(this.city.getName()!=""){
            for(Area area:this.city.getAreas()){
                if(area.getName().contains(str)){
                    this.area=area;
                    int len=area.getName().length();
                    for(int i=0;i<len;i++)
                    {
                        if(string.charAt(i)!=area.getName().charAt(i))
                        {
                            len=i;
                            break;
                        }
                    }
                    string=string.substring(len);
                }
            }
        }
        else{
            for(City city:this.province.getCities()){
                for(Area area:city.getAreas()){
                    if(area.getName().contains(str)){
                        this.area=area;
                        int len=area.getName().length();
                        for(int i=0;i<len;i++)
                        {
                            if(string.charAt(i)!=area.getName().charAt(i))
                            {
                                len=i;
                                break;
                            }
                        }
                        string=string.substring(len);
                    }
                }
            }

        }
    }
    private void sepStreet(){
        String str=string.substring(0,2);
        //System.out.println(str);
        if(this.area.getName()!=""){
            //System.out.println(area.getStreets());
            for(Street street:this.area.getStreets()){
                if(street.getName().contains(str)){
                    this.street=street;
                    int len=Math.min(street.getName().length(),string.length());
                    for(int i=0;i<len;i++)
                    {
                        if(string.charAt(i)!=street.getName().charAt(i))
                        {
                            len=i;
                            break;
                        }
                    }
                    string=string.substring(len);
                    //System.out.println(string);
                }
            }//System.out.println(street.getName());
        }
        else{
            //System.out.println("1");
            for(Area area:this.city.getAreas()){
                for(Street street:area.getStreets()){
                    if(street.getName().contains(str)){
                        this.street=street;
                        int len=street.getName().length();
                        for(int i=0;i<len;i++)
                        {
                            if(string.charAt(i)!=street.getName().charAt(i))
                            {
                                len=i;
                                break;
                            }
                        }
                        string=string.substring(len);
                        //System.out.println(string);
                    }
                }//System.out.println(street.getName());
            //this.street=new Street();
        }}
    }
    private void sepDetails() {
        //System.out.println("asdasdasdasd");
        String road = "";
        String number = "";
        String splitter = "(.*[镇区道路街巷里])";
        Pattern pattern = Pattern.compile(splitter);
        Matcher matcher = pattern.matcher(string);
        //System.out.println(string);
        //System.out.println(matcher.find());
        if (matcher.find()) {
            road = matcher.group();
            //System.out.println(road);
            int len=road.length();
            for(int i=0;i<len;i++)
            {
                if(string.charAt(i)!=road.charAt(i))
                {
                    len=i;
                    break;
                }
            }
            string=string.substring(len);
        }

        splitter = "(\\d+号楼.*)";
        pattern = Pattern.compile(splitter);
        matcher = pattern.matcher(string);
        if (matcher.find()) {
            string = matcher.group();
        } else {
            splitter = "(\\d+号)";
            pattern = Pattern.compile(splitter);
            matcher = pattern.matcher(string);
            if (matcher.find()) {
                number = matcher.group();
                int len=number.length();
                for(int i=0;i<len;i++)
                {
                    if(string.charAt(i)!=number.charAt(i))
                    {
                        len=i;
                        break;
                    }
                }
                string=string.substring(len);
            }
        }
        //System.out.println(road);
        addressList.add(road);
        addressList.add(number);
        addressList.add(string);
    }
    public Separation separation(){
        sepLeveAndName();
        sepPhonenum();
        sepProvince();
        sepCity();
        sepArea();
        sepStreet();
        addressList.add(this.province.getName());
        addressList.add(this.city.getName());
        addressList.add(this.area.getName());
        if(this.street!=null)
        addressList.add(this.street.getName());
        //System.out.println("+"+leve+"+");
        //System.out.println(string);
        if(leve.equals("1")) {
            addressList.add(string);
        } else {
            sepDetails();
        }
        return this;
    }

    public Result toResult() {
        return new Result(name, phonenum, addressList);
    }
}