//自由驱动工作室
//作者：林鑫
$(function(){
        var Initials=$('.initials');
        var LetterBox=$('#letter');
        Initials.find('ul').append('<li>A</li><li>B</li><li>C</li><li>D</li><li>E</li><li>F</li><li>G</li><li>H</li><li>I</li><li>J</li><li>K</li><li>L</li><li>M</li><li>N</li><li>O</li><li>P</li><li>Q</li><li>R</li><li>S</li><li>T</li><li>U</li><li>V</li><li>W</li><li>X</li><li>Y</li><li>Z</li><li>#</li>');
        initials();

        $(".initials ul li").click(function(){
            var _this=$(this);
            var LetterHtml=_this.html();
            LetterBox.html(LetterHtml).fadeIn();

            Initials.css('background','rgba(145,145,145,0.6)');
            
            setTimeout(function(){
                Initials.css('background','rgba(145,145,145,0)');
                LetterBox.fadeOut();
            },1000);

            var _index = _this.index()
            if(_index==0){
                $('html,body').animate({scrollTop: '0px'}, 300);//点击第一个滚到顶部
            }else if(_index==27){
                var DefaultTop=$('#default').position().top;
                $('html,body').animate({scrollTop: DefaultTop+'px'}, 300);//点击最后一个滚到#号
            }else{
                var letter = _this.text();
                if($('#'+letter).length>0){
                    var LetterTop = $('#'+letter).position().top;
                    $('html,body').animate({scrollTop: LetterTop-45+'px'}, 300);
                }
            }
        })

        var windowHeight=$(window).height();
        var InitHeight=windowHeight-45;
        Initials.height(InitHeight);
        var LiHeight=InitHeight/28;
        Initials.find('li').height(LiHeight);
})

function initials() {//公众号排序
    var city2=["北京","上海","深圳","汕头","湖南","湖北","河南","河北"] ;
	var city=[{ "val":"北京22" , "text":"北京" },
{ "val":"上海22" , "text":"上海" },
{ "val":"深圳22" , "text":"深圳" },
{ "val":"汕头22" , "text":"汕头" },
{ "val":"湖南22" , "text":"湖南" },
{ "val":"湖北22" , "text":"湖北" },
{ "val":"河南22" , "text":"河南" }] ;
    
   city.sort(asc_sort);//按首字母排序
   function asc_sort(a, b) {
        return makePy(b.text.charAt(0))[0].toUpperCase() < makePy(a.text.charAt(0))[0].toUpperCase() ? 1 : -1;
    }
	 for (var i =0;i<city.length;i++) {//插入到对应的首字母后面
        var letter=makePy(city[i]["text"].charAt(0))[0].toUpperCase();
        switch(letter){
            case "A":
                $('#A').after(city[i].val);
                break;
            case "B":
                $('#B').after(city[i].val);
                break;
            case "C":
                $('#C').after(city[i].val);
                break;
            case "D":
                $('#D').after(city[i].val);
                break;
            case "E":
                $('#E').after(city[i].val);
                break;
            case "F":
                $('#F').after(city[i].val);
                break;
            case "G":
                $('#G').after(city[i].val);
                break;
            case "H":
                $('#H').after(city[i].val);
                break;
            case "I":
                $('#I').after(city[i].val);
                break;
            case "J":
                $('#J').after(city[i].val);
                break;
            case "K":
                $('#K').after(city[i].val);
                break;
            case "L":
                $('#L').after(city[i].val);
                break;
            case "M":
                $('#M').after(city[i].val);
                break;
            case "N":
                $('#N').after(city[i].val);
                break;
            case "O":
                $('#O').after(city[i].val);
                break;
            case "P":
                $('#P').after(city[i].val);
                break;
            case "Q":
                $('#Q').after(city[i].val);
                break;
            case "R":
                $('#R').after(city[i].val);
                break;
            case "S":
                $('#S').after(city[i].val);
                break;
            case "T":
                $('#T').after(city[i].val);
                break;
            case "U":
                $('#U').after(city[i].val);
                break;
            case "V":
                $('#V').after(city[i].val);
                break;
            case "W":
                $('#W').after(city[i].val);
                break;
            case "X":
                $('#X').after(city[i].val);
                break;
            case "Y":
                $('#Y').after(city[i].val);
                break;
            case "Z":
                $('#Z').after(city[i].val);
                break;
            default:
                $('#default').after(city[i].val);
                break;
        }
    };
/*
	var SortList=$(".sort_list");
    var SortBox=$(".sort_box");
    SortList.sort(asc_sort).appendTo('.sort_box');//按首字母排序
	function asc_sort(a, b) {
        return makePy($(b).find('.num_name').text().charAt(0))[0].toUpperCase() < makePy($(a).find('.num_name').text().charAt(0))[0].toUpperCase() ? 1 : -1;
    }
	

    var initials = [];
    var num=0;
    SortList.each(function(i) {
        var initial = makePy($(this).find('.num_name').text().charAt(0))[0].toUpperCase();
        if(initial>='A'&&initial<='Z'){
            if (initials.indexOf(initial) === -1)
                initials.push(initial);
        }else{
            num++;
        }
        
    });
if(num!=0){SortBox.append('<div class="sort_letter" id="default">#</div>');}
    $.each(initials, function(index, value) {//添加首字母标签
        SortBox.append('<div class="sort_letter" id="'+ value +'">' + value + '</div>');
    });
  
	



    for (var i =0;i<SortList.length;i++) {//插入到对应的首字母后面
        var letter=makePy(SortList.eq(i).find('.num_name').text().charAt(0))[0].toUpperCase();
        switch(letter){
            case "A":
                $('#A').after(SortList.eq(i));
                break;
            case "B":
                $('#B').after(SortList.eq(i));
                break;
            case "C":
                $('#C').after(SortList.eq(i));
                break;
            case "D":
                $('#D').after(SortList.eq(i));
                break;
            case "E":
                $('#E').after(SortList.eq(i));
                break;
            case "F":
                $('#F').after(SortList.eq(i));
                break;
            case "G":
                $('#G').after(SortList.eq(i));
                break;
            case "H":
                $('#H').after(SortList.eq(i));
                break;
            case "I":
                $('#I').after(SortList.eq(i));
                break;
            case "J":
                $('#J').after(SortList.eq(i));
                break;
            case "K":
                $('#K').after(SortList.eq(i));
                break;
            case "L":
                $('#L').after(SortList.eq(i));
                break;
            case "M":
                $('#M').after(SortList.eq(i));
                break;
            case "N":
                $('#N').after(SortList.eq(i));
                break;
            case "O":
                $('#O').after(SortList.eq(i));
                break;
            case "P":
                $('#P').after(SortList.eq(i));
                break;
            case "Q":
                $('#Q').after(SortList.eq(i));
                break;
            case "R":
                $('#R').after(SortList.eq(i));
                break;
            case "S":
                $('#S').after(SortList.eq(i));
                break;
            case "T":
                $('#T').after(SortList.eq(i));
                break;
            case "U":
                $('#U').after(SortList.eq(i));
                break;
            case "V":
                $('#V').after(SortList.eq(i));
                break;
            case "W":
                $('#W').after(SortList.eq(i));
                break;
            case "X":
                $('#X').after(SortList.eq(i));
                break;
            case "Y":
                $('#Y').after(SortList.eq(i));
                break;
            case "Z":
                $('#Z').after(SortList.eq(i));
                break;
            default:
                $('#default').after(SortList.eq(i));
                break;
        }
    };


	*/
}