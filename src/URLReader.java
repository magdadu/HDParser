import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

class URLReader {

	public static void main(String[] args) throws Exception {
		int i = 0;
		int j = 0;
		Document doc = Jsoup.connect("http://programtv.se.pl/archiwum-tv/").timeout(0).get();
		Elements nazwyKanalow = doc.select(".tvArch a");
		Document doc3 = Jsoup.connect("http://programtv.se.pl/archiwum-tv/tvp-1,80/").timeout(0).get();
		Elements dzien = doc3.select(".tvArch_singiel .tvArch .archRight a");
		String regexp = "2{1}0{1}\\d{2}-{1}\\d{2}-{1}\\d{2}";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(dzien.toString());
		List<String> l = new ArrayList<>();
		
		
		while (matcher.find()) {
			
			if (matcher.group().substring(0, 4).equals("2012"))
				l.add(matcher.group());
		}

		for (Element e : nazwyKanalow) {
			
			
			ExcelCreator ex = new ExcelCreator(e.text());
			
		
			
			for (String m : l) {
			
				Document doc4 = Jsoup.connect("http://programtv.se.pl"+e.attr("href") + m + "/").timeout(0).ignoreHttpErrors(true).get();
				Elements time = doc4.select(".zpr_screening .program-row .time ");
				Elements name = doc4.select(".program-row .name ");
				for (Element e2 : time) {
					ex.createRow(j);
	
					ex.insertValue(0, e.text().toString());
					ex.insertValue(1, m.toString()+" "+e2.text().toString().substring(0,5));
					ex.insertValue(2,  m.toString()+" "+e2.text().toString().substring(6,11));// godzina
					ex.insertValue(3, name.get(i).text().toString());// nazwa
					i++;
					j++;
				}
				i = 0;
			}
			
			j = 0;
			ex.generate();
		}
		System.exit(0);
	}
}