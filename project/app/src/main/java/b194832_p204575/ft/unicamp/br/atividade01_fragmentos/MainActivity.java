package b194832_p204575.ft.unicamp.br.atividade01_fragmentos;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.widget.Toast;

import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.alunos.AlunosFragment;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.database.DatabaseFragment;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.interfaces.OnBiografiaRequest;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.internet.InternetFragment;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo2.EstatisticaFragment;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo2.NameFragment;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo3.AlunosFraseFragment;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo3.FrasesAlunoFragment;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo3.Jogo3Fragment;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo3.estatisticas.EstatFrasesAluno;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnBiografiaRequest {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            MyFirstFragment f1 = new MyFirstFragment();
            fragmentTransaction.add(R.id.frame, f1, "f1_tag");
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.stats_id) {
            Toast.makeText(this, "Estatísticas Jogo 2", Toast.LENGTH_SHORT).show();
            if(fragmentManager.findFragmentByTag("estatistica_tag") == null){
                EstatisticaFragment estatisticaFragment = new EstatisticaFragment();
                replaceFragment(estatisticaFragment, "estatistica_tag");
            }
            return true;

        } else if (id == R.id.action_contato) {
            Toast.makeText(this, "Contato", Toast.LENGTH_SHORT).show();
            if(fragmentManager.findFragmentByTag("contato_tag") == null){
                MailFragment fragContato = new MailFragment();
                replaceFragment(fragContato, "contato_tag");
            }
            return true;
        }  else if (id == R.id.stats_id_jogo3) {
            Toast.makeText(this, "Estatística Jogo 3", Toast.LENGTH_SHORT).show();
            Fragment estatFrasesAluno = fragmentManager.findFragmentByTag("estatFrasesAluno_fragment");
            if (estatFrasesAluno == null) {
                estatFrasesAluno = new EstatFrasesAluno();
            }
            replaceFragment(estatFrasesAluno, "estatFrasesAluno_fragment");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_autores) {
            Toast.makeText(this, "Autores", Toast.LENGTH_SHORT).show();
            if(fragmentManager.findFragmentByTag("autores_tag") == null){
                AutoresFragment fragAutores = new AutoresFragment();
                replaceFragment(fragAutores, "autores_tag");
            }

        } else if (id == R.id.nav_alunos) {
            if(fragmentManager.findFragmentByTag("aluno_tag") == null){
                AlunosFragment fragAlunos = new AlunosFragment();
                replaceFragment(fragAlunos, "aluno_tag");

                fragAlunos.setMyReplaceFragment(
                        new AlunosFragment.MyReplaceFragment() {
                            @Override
                            public void replaceBiografia(int position) {
                                BiografiaFragment bioFrag = new BiografiaFragment();
                                bioFrag.setPosition(position);
                                replaceFragment(bioFrag, "biografia_tag");
                            }
                        }
                );

            }

        } else if (id == R.id.nav_biografias) {
            Toast.makeText(this, "Biografias", Toast.LENGTH_SHORT).show();
            if(fragmentManager.findFragmentByTag("biografia_tag") == null){
                BiografiaFragment fragBiografia = new BiografiaFragment();
                replaceFragment(fragBiografia, "biografia_tag");
            }
        } else if (id == R.id.nav_jogo1) {
            Toast.makeText(this, "Jogo 1", Toast.LENGTH_SHORT).show();
            if(fragmentManager.findFragmentByTag("jogo1_tag") == null){
                PuzzleFragment fragPuzzle = new PuzzleFragment();
                replaceFragment(fragPuzzle, "jogo1_tag");
            }
        } else if (id == R.id.nav_jogo2) {
            NameFragment nameFragment = (NameFragment) fragmentManager.findFragmentByTag("name");
            if (nameFragment == null) {
                nameFragment = new NameFragment();
                nameFragment.setOnBiografiaRequest(this);
            }
            replaceFragment(nameFragment, "name");
        }else if (id == R.id.nav_jogo3){
            Fragment alunosFrase = fragmentManager.findFragmentByTag("alunosFrase_Fragment");
            if (alunosFrase == null)
                alunosFrase = new AlunosFraseFragment();
            replaceFragment(alunosFrase, "alunosFrase_Fragment");

        } else if (id == R.id.nav_banco_de_dados) {
            Fragment dataBaseFragment = fragmentManager.findFragmentByTag("database_Fragment");
            if(dataBaseFragment == null){
                dataBaseFragment  = new DatabaseFragment();
                replaceFragment(dataBaseFragment, "database_Fragment");
            }
        } else if (id == R.id.nav_internet) {
            Fragment internetFragment = fragmentManager.findFragmentByTag("internetFragment");
            if(internetFragment == null){
                internetFragment  = new InternetFragment();
                replaceFragment(internetFragment, "internetFragment");
            }
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void replaceFragment(Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, tag);
        fragmentTransaction.commit();
    }

    public void clickSend(String msg) {
        AutoresFragment autorFragment = (AutoresFragment)fragmentManager.findFragmentByTag("autores_tag");
        if (autorFragment == null){
            autorFragment = new AutoresFragment();
        }

        autorFragment.setText(msg);
        replaceFragment(autorFragment, "autores_tag");
    }

    @Override
    public void onRequest(int position) {
        BiografiaFragment bioFrag = new BiografiaFragment();
        bioFrag.setPosition(position);
        replaceFragment(bioFrag, "biografia_tag");
    }

    public void replaceAlunosfrase(){
        Fragment alunosFrase = fragmentManager.findFragmentByTag("alunosFrase_Fragment");
        if (alunosFrase == null)
            alunosFrase = new AlunosFraseFragment();
        replaceFragment(alunosFrase, "alunosFrase_Fragment");
    }

    public void replaceFrasesAluno(){
        Fragment alunosFrase = fragmentManager.findFragmentByTag("frasesAluno_Fragment");
        if (alunosFrase == null)
            alunosFrase = new FrasesAlunoFragment();
        replaceFragment(alunosFrase, "frasesAluno_Fragment");
    }

}