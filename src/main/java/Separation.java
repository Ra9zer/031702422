
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
            string = str[0] + str[1];
        }
    }
    private void sepProvince(){
        String str=string.substring(0,2);
        for(Province province:DataBase.getProvinces()){
            if(province.getName().contains(str)){
                if(province.getName().equals("北京")||
                        province.getName().equals("天津")||
                        province.getName().equals("上海")||
                        province.getName().equals("重庆")){
                    string=province.getName()+string;
                }
                this.province=province;
                int len=province.getName().length();
                for(int i=0;i<len;i++)
                {
                    if(string.charAt(i)!=province.getName().charAt(i))
                    {
                        len=i;
                        break;
                    }
                }
                string=string.substring(len);
                break;
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
        if(this.city!=null){
            String str=string.substring(0,2);
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
            this.area=new Area();
        }
    }
    private void sepStreet(){
        if(this.area!=null){
            String str=string.substring(0,2);
            //System.out.println(area.getName());
            //System.out.println(area.getStreets());
            for(Street street:this.area.getStreets()){
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
                }
            }
        }
        else{
            this.street=new Street();
        }
    }
    private void sepDetails() {
        String splitter = "(\\D+)(\\d+号)(.*)";
        Pattern pattern = Pattern.compile(splitter);
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            String road = matcher.group(1);
            String number = matcher.group(2);
            String last = matcher.group(3);
            addressList.add(road);
            addressList.add(number);
            addressList.add(last);
        }
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
        switch (leve) {
            case "1":
                addressList.add(string);
                break;
            case "2":
                sepDetails();
                break;
            case "3":
                sepDetails();
                break;
            default:
                break;
        }
        return this;
    }

    public Result toResult() {
        return new Result(name, phonenum, addressList);
    }
}