package com.example.webcomic4u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.widget.Toolbar;

import com.example.webcomic4u.Adaptor.CategoryAdaptor;
import com.example.webcomic4u.Adaptor.MangaAdaptor;
import com.example.webcomic4u.Domain.CategoryDomain;
import com.example.webcomic4u.Domain.MangaDomain;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
private RecyclerView.Adapter adapter,adapter2;
private RecyclerView recyclerViewCategoryList,recyclerViewMangaList;
DrawerLayout drawerLayout;
NavigationView navigationView;
Toolbar toolbar;
ListView listView;
VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewCategory();

        recyclerViewManga();

        bottomNavigation();

        search();

        //
        TextView textView = findViewById(R.id.dathangngay);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });

        //Video view
        videoView = findViewById(R.id.videoview);
        Uri uri = Uri.parse("android.resource://"+ getPackageName()+"/"+R.raw.bg_video2);
        videoView.setVideoURI(uri);
        videoView.start();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });



        //Navigation drawer
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        navigationView.bringToFront();
        setSupportActionBar(toolbar);
        Menu menu = navigationView.getMenu();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_home);

    }
    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }




    public void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout tim_kiemBtn = findViewById(R.id.tim_kiemBtn);
        LinearLayout logout = findViewById(R.id.logoutBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
        tim_kiemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SearchActivity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, IntroActivity.class));
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewCategoryList = findViewById(R.id.recyclerView);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> category = new ArrayList<>();
        category.add(new CategoryDomain("Hành động",R.drawable.genre1));
        category.add(new CategoryDomain("Lãng mạn",R.drawable.genre2));
        category.add(new CategoryDomain("Kinh dị",R.drawable.genre3));
        category.add(new CategoryDomain("Hài kịch",R.drawable.genre4));
        category.add(new CategoryDomain("Drama",R.drawable.genre5));
        category.add(new CategoryDomain("Phép thuật",R.drawable.genre6));
        category.add(new CategoryDomain("Phiêu lưu",R.drawable.genre7));
        category.add(new CategoryDomain("Ảo tưởng",R.drawable.genre8));
        category.add(new CategoryDomain("Siêu năng",R.drawable.genre9));
        category.add(new CategoryDomain("Học đường",R.drawable.genre10));
        category.add(new CategoryDomain("Ma quỷ",R.drawable.genre11));
        category.add(new CategoryDomain("Võ thuật",R.drawable.genre12));
        category.add(new CategoryDomain("Lịch sử",R.drawable.genre13));
        category.add(new CategoryDomain("Siêu nhiên",R.drawable.genre14));

        adapter = new CategoryAdaptor(category);
        recyclerViewCategoryList.setAdapter(adapter);
    }

    private void recyclerViewManga() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerViewMangaList = findViewById(R.id.recyclerView2);
        recyclerViewMangaList.setLayoutManager(linearLayoutManager);

        ArrayList<MangaDomain> mangaList = new ArrayList<>();
        mangaList.add(new MangaDomain("One Piece","onepiece","Đạo diễn: Uda Kounosuke ,Ishitani Megumi - Thể loại: Anime bộ, Shounen, Siêu năng lực, Ảo tưởng, Drama, Hài kịch, Phiêu lưu, Hành động - Mô tả: Đảo Hải Tặc - One Piece là chuyện về cậu bé Monkey D. Luffy do ăn nhầm Trái Ác Quỷ, bị biến thành người cao su và sẽ không bao giờ biết bơi. 10 năm sau sự việc đó, cậu rời quê mình và kiếm đủ 10 thành viên để thành một băng hải tặc, biệt hiệu Hải tặc Mũ Rơm. Khi đó của phiêu lưu tìm kiếm kho báu One Piece bắt đầu. Trong cuộc phiêu lưu tìm kiếm One Piece, băng Hải tặc mũ rơm phải chiến đấu với nhiều băng hải tặc xấu khác cũng muốn độc chiếm One Piece và Hải quân của Chính phủ muốn diệt trừ hải tặc. Băng Hải tặc Mũ Rơm phải trải qua biết bao nhiêu khó khăn, không lùi bước với ước mơ \"Trở thành Vua Hải Tặc và chiếm được kho báu One Piece\".", 85.999));
        mangaList.add(new MangaDomain("Chainsaw Man","chainsawman","Đạo diễn: Nakayama Ryuu, Yoshihara Tatsuya - Thể loại: Anime bộ, Shounen, Ma quỷ, Phiêu lưu, Hành động - Mô tả: Câu chuyện lấy bối cảnh tại một thế giới tồn tại những con quỷ đáng sợ. Lũ quỷ này hình thành từ chính những nỗi sợ của con người như sợ bóng tối, sợ cá mập, sợ nhện, sợ súng, sợ bạo lực,… Để chống lại chúng, các tổ chức săn quỷ trực thuộc chính phủ đã được thành lập trên toàn thế giới.Nhân vật chính của Chainsaw Man là Denji. Với mục tiêu trả số tiền nợ khổng lồ mà cha mình để lại, Denji đã phải bán nội tạng và làm bất kì công việc nào, kể cả diệt quỷ. Bên cạnh Denji là Pochita, một con quỷ nhỏ bé với lưỡi cưa trên đầu. Pochita cũng là người bạn duy nhất cùng Denji sống những ngày cơ cực không có gì để ăn. Trong một lần bị bọn chủ nợ chơi xỏ, Denji đã bị giết. Pochita liền hòa làm một với Denji và hồi sinh cậu ta thành “Quỷ Cưa” hùng mạnh. Sau khi đã trả thù những kẻ xấu, Denji được Makima, thành viên tổ chức săn quỷ tại Nhật Bản “nhận nuôi”. Câu chuyện của Chainsaw Man bắt đầu từ đây.", 77.099));
        mangaList.add(new MangaDomain("Blue Lock","bluelock","Đạo diễn: Watanabe Tetsuaki - Thể loại: Anime bộ, Shounen, Thể thao - Mô tả: Yoichi Isagi đã bỏ lỡ cơ hội tham dự giải Cao Trung toàn quốc do đã chuyền cho đồng đội thay vì tự thân mình dứt điểm. Cậu là một trong 300 chân sút U-18 được tuyển chọn bởi Jinpachi Ego, người đàn ông được Hiệp Hội Bóng Đá Nhật Bản thuê sau hồi FIFA World Cup năm 2018, nhằm dẫn dắt Nhật Bản vô địch World Cup bằng cách tiêu diệt nền bóng đá Nhật Bản. Kế hoạch của Ego gồm việc cô lập 300 tay sút trong một nhà ngục - dưới một tổ chức với tên gọi là \"Blue Lock\", với mục tiêu là tạo ra chân sút \"tự phụ\" nhất thế giới, điều mà nền bóng đá Nhật Bản còn thiếu.", 89.199));
        mangaList.add(new MangaDomain("Naruto","naruto","Đạo Diễn: Hayato Date - Thể loại: Anime bộ, Shounen, Siêu năng lực, Phiêu lưu, Võ thuật, Hành động - Mô tả: Naruto là câu chuyện về Uzumaki Naruto, một ninja trẻ với ước mơ trở thành Hokage, người lãnh đạo của làng Lá để tìm kiếm sự công nhận từ mọi người. 12 năm trước, hồ ly 9 đuôi tấn công làng Lá, Hokage Đệ Tứ đã đánh bại và phong ấn hồ ly 9 đuôi vào con trai của chính mình - Naruto nhằm kết thúc cuộc tấn công đẫm máu này. Sau trận chiến đó, Naruto bị mất cả cha lẫn mẹ, cậu bị mọi người xa lánh vì mang trong mình con quái vật đã giết chết biết bao người dân làng Lá. Sau khi vượt qua kỳ thi tốt nghiệp, Naruto cùng hai người bạn đồng hành là Uchiha Sasuke và Haruno Sakura được thầy Kakashi dẫn dắt, tạo nên đội 7 trứ danh, chính thức bắt đầu cuộc phiêu lưu. Khi xem Naruto, khán giả sẽ được chứng kiến quá trình trưởng thành hàng ngày của cậu bé thông qua những tập phim, những trận chiến đấu cùng thầy cô và các đồng đội.", 64.299));
        mangaList.add(new MangaDomain("Tokyo Revengers","tokyo","Đạo diễn: Hatsumi Kouichi - Thể loại: Anime bộ, Shounen, Học đường, Siêu nhiên, Drama, Hành động - Mô tả: Takemichi, thanh niên thất nghiệp còn trinh, được biết rằng người con gái đầu tiên và cũng là duy nhất cho đến bây giờ mà anh hẹn hò từ trung học đã chết. Sau một vụ tai nạn, anh ta thấy mình được quay về những ngày cấp hai. Anh ta thề sẽ thay đổi tương lai và giữ lấy người con gái ấy, để làm việc đó, anh ta quyết định sẽ vươn lên làm trùm băng đảng khét tiếng nhất ở vùng Kantou.", 105.499));
        mangaList.add(new MangaDomain("Demon Slayer","demon","Đạo diễn: Kondou Hikaru - Thể loại: Anime bộ, Shounen, Siêu nhiên, Ma quỷ, Lịch sử, Hành động - Mô tả: Thanh Gươm Diệt Quỷ còn có tên gọi khác là Lưỡi Gươm Diệt Quỷ hay Yaiba Kimetsu. Phim Thanh Gươm Diệt Quỷ: Kimetsu No Yaiba. Vào thời xa xưa, có một truyền thuyết về những con quỷ ăn thịt người luôn lãng vãng trong cánh rừng sâu. Vì điều này, người dân trong làng không bao giờ đi ra ngoài vào trời tối. Truyền thuyến cũng kể rằng có một Sát Quỷ Nhân luôn xuất hiện vào ban đêm để tiêu diệt những con quỷ khát máu này. Đối với chàng trai trẻ Tanjiro, câu chuyện dân gian ấy đã trở thành hiện thực cay đắng mà cậu phải hứng chịu...Sau khi cha cậu qua đời, Tanjiro luôn cố gắng hết sức để trở thành trụ cột trong gia đình. Dù cuộc sống vất vả, cực khổ, những gia đình của cậu vẫn luôn ngập tràn hạnh phúc. Nhưng niềm hạnh phúc ấy cũngkhông kéo dài lâu được. Một ngày nọ, khi Tanjiro trở về, cậu phát hiện gia đình mình đã bị sát hại dã man, và chỉ có một người duy nhất sống sót, đó là em gái Nezuko của cậu. Nhưng bi kịch thay, Nezuko đã trở thành con quỷ đáng sợ. Tuy nhiên, cô vẫn còn giữ một chút nhân tính. Đó là thời điểm bắt đầu cuộc chiến đơn độc của Tanjiro, tiêu diệt quỷ và tìm cách biến em gái mình trở lại làm người.", 99.999));
        mangaList.add(new MangaDomain("My Hero Academia","bokuhero","Đạo diễn: Nagasaki Kenji - Thể loại: Anime bộ, Shounen, Học đường, Siêu năng lực, Hài kịch, Hành động - Mô tả: Thế giới sẽ như thế nào nếu 80% dân số bộc phát những năng lực đặc biệt từ lúc 4 tuổi? Anh hùng và tội phạm sẽ đối đầu với nhau không ngừng nghỉ. Làm anh hùng đồng nghĩa với việc học cách sử dụng năng lực của mình, nhưng ta sẽ học ở đâu? Tất nhiên là chương trình đào tạo anh hùng của A.U rồi! Nhưng sẽ thế nào nếu bạn nằm trong 20% dân số không có năng lực? Izuku Midoriya, một học sinh cấp 2, rất khao khát muốn trở thành anh hùng, nhưng cậu lại không hề có sức mạnh. Không có cơ hội vào được trường đào tạo anh hùng, cuộc đời cậu tưởng như đã kết thúc. Nhưng cuộc gặp gỡ với All Might, anh hùng vĩ đại nhất đã cho cậu cơ hội thay đổi số mệnh của mình…", 116.299));
        mangaList.add(new MangaDomain("Black Clover","blackclover","Đạo diễn: Yoshihara Tatsuya - Thể loại: Anime bộ, Shounen, Ảo tưởng, Hài kịch, Phép thuật, Hành động - Mô tả: Asta và Yuno đã bị bỏ rơi cùng nhau tại cùng một nhà thờ và đã không thể tách rời kể từ đó. Khi còn trẻ, họ hứa sẽ cạnh tranh với nhau để xem ai sẽ trở thành Hoàng đế Magus tiếp theo. Tuy nhiên, khi chúng lớn lên, một số khác biệt giữa chúng trở nên đơn giản. Yuno là một thiên tài với ma thuật, với sức mạnh và khả năng kiểm soát tuyệt vời, trong khi Asta không thể sử dụng ma thuật chút nào, và cố gắng bù đắp cho sự thiếu hụt của mình bằng cách luyện tập thể chất. Khi họ nhận được Grimoa của họ ở tuổi 15, Yuno đã có một cuốn sách ngoạn mục với cỏ ba lá bốn lá (hầu hết mọi người đều có một cây cỏ ba lá), trong khi Asta không nhận được gì cả. Tuy nhiên, khi Yuno bị đe dọa, sự thật về sức mạnh của Asta đã được tiết lộ, anh đã nhận được một cây cỏ ba lá Grimoire, một cỏ ba lá đen! Bây giờ hai người bạn đang hướng tới trên thế giới, cả hai đều mong muốn cùng một mục tiêu!", 62.499));
        mangaList.add(new MangaDomain("Attack on Titan","attackontitan","Đạo diễn: Wada Jouji, Yabuta Shuuhei - Thể loại: Anime bộ, Shounen, Siêu năng lực, Ảo tưởng, Drama, Hành động - Mô tả: Eren Jaeger sống trong một thành phố bao bọc bởi tường đá. Titan giết người ở ngoài đó. Trong nhiều thập kỉ, thành viên của Nhóm trinh thám Legion là những con người duy nhất dám bước ra khỏi bức tường và thu thập thông tin về những Titan. Eren, một người yêu hòa bình, không còn nguyện vọng nào to lớn việc gia nhập họ.", 97.999));
        mangaList.add(new MangaDomain("Spy x Family","spyfamily","Đạo diễn: Furuhashi Kazuhiro - Thể loại: Anime bộ, Shounen, Hài kịch, Hành động - Mô tả: Mỗi người đều có một phần bí mật của mình mà họ không thể cho ai khác biết.Vào thời điểm mà tất cả các quốc gia trên thế giới đang tham gia vào một cuộc chiến tranh thông tin khốc liệt xảy ra sau những cánh cửa đóng kín, Ostania và Westalis đã ở trong tình trạng chiến tranh lạnh với nhau trong nhiều thập kỷ.Bộ phận Tập trung vào phía Đông của Dịch vụ Tình báo Westalis (WISE) cử điệp viên tài năng nhất của họ, \"Twilight\", theo mật vụ tối mật để điều tra các chuyển động của Donovan Desmond, chủ tịch Đảng Thống nhất Quốc gia của Ostania, người đang đe dọa các nỗ lực hòa bình giữa hai quốc gia.Nhiệm vụ này được gọi là \"Chiến dịch Strix.\"Nó bao gồm \"tập hợp một gia đình trong một tuần để xâm nhập vào các cuộc tụ họp xã hội được tổ chức bởi ngôi trường ưu tú mà con trai Desmond theo học.\"\"Twilight\" lấy nhân dạng của bác sĩ tâm thần Loid Forger và bắt đầu tìm kiếm các thành viên trong gia đình. Nhưng Anya, cô con gái mà anh nhận nuôi, hóa ra lại có khả năng đọc được suy nghĩ của mọi người, trong khi vợ anh Yor lại là một sát thủ! Vì lợi ích riêng mỗi người đều giữ kín những bí mật này, họ bắt đầu sống cùng nhau và không để lộ danh tính thật của mình với nhau.Hòa bình thế giới giờ đây nằm trong tay của gia đình mới này khi họ dấn thân vào một cuộc phiêu lưu đầy bất ngờ.", 93.199));
        mangaList.add(new MangaDomain("Jujutsu Kaisen","jujutsu","Đạo diễn: Park Seong-Hu - Thể loại: Anime bộ, Shounen, Học đường, Siêu nhiên, Ma quỷ, Kinh dị, Hành động - Mô tả: Trong một thế giới nơi những con quỷ ăn thịt người không nghi ngờ gì, những mảnh vỡ của con quỷ huyền thoại và đáng sợ Ryoumen Sukuna đã bị thất lạc và nằm rải rác. Nếu bất kỳ con quỷ nào tiêu thụ các bộ phận cơ thể của Sukuna, sức mạnh mà chúng có được có thể phá hủy thế giới như chúng ta đã biết. May mắn thay, có một ngôi trường bí ẩn của các Phù thủy Jujutsu tồn tại để bảo vệ sự tồn tại bấp bênh của người sống khỏi xác sống!Yuuji Itadori là một học sinh trung học dành cả ngày để thăm ông nội nằm liệt giường của mình. Mặc dù anh ấy trông giống như một thiếu niên bình thường của bạn, nhưng sức mạnh thể chất to lớn của anh ấy là một điều đáng chú ý! Mọi câu lạc bộ thể thao đều muốn cậu tham gia, nhưng Itadori thà đi chơi với những đứa trẻ bị trường ruồng bỏ trong Câu lạc bộ huyền bí. Một ngày nọ, câu lạc bộ quản lý để có được bàn tay của họ trên một vật thể bị nguyền rủa bị phong ấn, nhưng họ ít biết nỗi kinh hoàng mà họ sẽ gây ra khi phá vỡ phong ấn ...", 84.299));
        mangaList.add(new MangaDomain("The Eminence in Shadow","shadow","Đạo diễn: Nakanishi Kazuya - Thể loại: Anime bộ, Ảo tưởng, Hài kịch, Hành động - Mô tả: Như bao người ngưỡng mộ những anh hùng khi còn nhỏ, nhân vật chính của chúng ta lại ngưỡng mộ những con người trong bóng tối. Sau khi giấu đi khả năng thật sự và sống 1 cuộc đời tầm thường của 1 thường dân vào ban ngày trong khi trải qua những khóa huấn luyện điên rồ vào ban đêm, anh ta cuối cùng cũng được chuyển sinh sang thê giới khác. Người đàn ông ấy, người mà chỉ phô diễn sức mạnh trong bóng tối, những người hầu cận và cùng với đó là tổ chức lớn mạnh của anh… Đây là câu chuyện về một chàng trai, người luôn khao khát sức mạnh trong màn đêm, trị vì thế giới ngầm ở thế giới khác.", 55.499));

        adapter2 = new MangaAdaptor(mangaList);
        recyclerViewMangaList.setAdapter(adapter2);


    }

    public void search() {
        ImageView searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,SearchActivity.class));
            }
        });

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                break;

            case R.id.nav_cart:
                Intent intent = new Intent(MainActivity.this,CartListActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_search:
                intent = new Intent(MainActivity.this,SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_info:
                intent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.nav_logout:
                intent = new Intent(MainActivity.this,IntroActivity.class);
                startActivity(intent);
                break;



        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPostResume() {
        videoView.resume();
        super.onPostResume();
    }

    @Override
    protected void onRestart() {
        videoView.start();
        super.onRestart();
    }

    @Override
    protected void onPause() {
        videoView.suspend();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        videoView.stopPlayback();
        super.onDestroy();
    }
}