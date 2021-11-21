import configuration.Configuration_Oracle;
import entity.Album;
import entity.Instrument;
import entity.Singer;
import entity.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import repository.SingerDao;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Executor {

    private static Logger logger = LoggerFactory.getLogger(Executor.class);

    public static void main(String[] args) throws InterruptedException {

        GenericApplicationContext ctx = new AnnotationConfigApplicationContext(Configuration_Oracle.class);
//        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext();
//        ctx.load(new ClassPathResource("configuration/configuration_oracle.xml"));
//        ctx.refresh();
//        activate @Repository("singerDao") in SingerDaoImpl

        SingerDao singerDao = ctx.getBean(SingerDao.class);

        listTests(singerDao.test());

        listSingers(singerDao.findAll());

        listSingersWithAlbums(singerDao.findAllWithAlbum());

        logSinger(singerDao.findById(1l));

        Singer singer = singerDao.save(createSinger());
        logSinger(singer);
        updateSinger(singer);
        logSinger(singer);
        Singer updatedSinger = singerDao.save(singer);
        logSinger(updatedSinger);
        Singer singerForDelete = singerDao.findById(updatedSinger.getId());
        logSinger(singerForDelete);
        singerDao.delete(singerForDelete);
    }

    private static void listTests(List<Test> tests) {
        for (Test test : tests)
            logger.info(test.toString());
    }

    private static void listSingers(List<Singer> singers) {
        for (Singer singer : singers)
            logger.info(singer.toString());
    }

    private static void listSingersWithAlbums(List<Singer> singers) {
        for (Singer singer : singers) {
            logger.info(singer.toString());
            if (singer.getAlbums() != null)
                for (Album album : singer.getAlbums())
                    logger.info(album.toString());
            if (singer.getInstruments() != null)
                for (Instrument instrument : singer.getInstruments())
                    logger.info(instrument.toString());
        }
    }

    private static void logSinger(Singer singer) {
        logger.info(singer.toString());
    }

    private static Singer createSinger() {
        Singer singer = new Singer();
        singer.setFirstName("BB");
        singer.setLastName("King");
        singer.setBirthDate(new Date((new GregorianCalendar(1940, 8, 16)).getTime().getTime()));
        Album album = new Album();
        album.setTitle("My Kind of Blues");
        album.setReleaseDate(new Date((new GregorianCalendar(1961, 7, 18)).getTime().getTime()));
        singer.addAlbum(album);
        album = new Album();
        album.setTitle("A Heart Full of Blues");
        album.setReleaseDate(new Date((new GregorianCalendar(1962, 3, 20)).getTime().getTime()));
        singer.addAlbum(album);
        return singer;
    }

    private static void updateSinger(Singer singer) {
        singer.setFirstName("AA");
        singer.setLastName("Queen");

        singer.getAlbums().removeIf(album -> album.getTitle().equals("My Kind of Blues"));
    }
}