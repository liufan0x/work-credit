var token;
var END_OF_INPUT = -1;
var base64Chars = new Array(
	    'A','B','C','D','E','F','G','H',
	    'I','J','K','L','M','N','O','P',
	    'Q','R','S','T','U','V','W','X',
	    'Y','Z','a','b','c','d','e','f',
	    'g','h','i','j','k','l','m','n',
	    'o','p','q','r','s','t','u','v',
	    'w','x','y','z','0','1','2','3',
	    '4','5','6','7','8','9','+','/'
	);

merge:function merge(fwxx,to) { 
	var data={};
 data.FWTYPE='0';
 data.fwxx=fwxx;
 token=to;
 //data.token='a0d329e7339644f29d1c185a4a17ee18';
 //token='e9d7b17a82a64b72a1b1c36cccd04740';
 //token='5c60a663a7ec4ccea3cbebc0c9a15b4c';
 return handleData(data);
}

function kwbms(p){
	if(p){
		for(var k in p){
			p[k] = kwbm(p[k]);
		}
	}
	return p;
}
function handleData(data){
	
	if(data){
		//if(jc_isencode && jc_isencode=="Y"){
            //data = kwbms(data);
            //if(jc_isencrypt && jc_isencrypt=="Y"){
            	kwfilters(kwencrypts(data));
           // }
       // }
	}
    return data;
}

function str2long(s, w) {
    var len = s.length;
    var v = [];
    for (var i = 0; i < len; i += 4) {
        v[i >> 2] = s.charCodeAt(i)
                  | s.charCodeAt(i + 1) << 8
                  | s.charCodeAt(i + 2) << 16
                  | s.charCodeAt(i + 3) << 24;
    }
    if (w) {
        v[v.length] = len;
    }
    return v;
}
function xxtea_encrypt(str, key) {
    if (str == "") {
        return "";
    }
    var v = str2long(str, true);
    var k = str2long(key, false);
    if (k.length < 4) {
        k.length = 4;
    }
    var n = v.length - 1;
 
    var z = v[n], y = v[0], delta = 0x9E3779B9;
    var mx, e, p, q = Math.floor(6 + 52 / (n + 1)), sum = 0;
    while (0 < q--) {
        sum = sum + delta & 0xffffffff;
        e = sum >>> 2 & 3;
        for (p = 0; p < n; p++) {
            y = v[p + 1];
            mx = (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
            z = v[p] = v[p] + mx & 0xffffffff;
        }
        y = v[0];
        mx = (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
        z = v[n] = v[n] + mx & 0xffffffff;
    }
 
    return long2str(v, false);
}
function long2str(v, w) {
    var vl = v.length;
    var n = (vl - 1) << 2;
    if (w) {
        var m = v[vl - 1];
        if ((m < n - 3) || (m > n)) return null;
        n = m;
    }
    for (var i = 0; i < vl; i++) {
        v[i] = String.fromCharCode(v[i] & 0xff,
                                   v[i] >>> 8 & 0xff,
                                   v[i] >>> 16 & 0xff,
                                   v[i] >>> 24 & 0xff);
    }
    if (w) {
        return v.join('').substring(0, n);
    }
    else {
        return v.join('');
    }
}

function encodeBase64(str){
    setBase64Str(str);
    var result = '';
    var inBuffer = new Array(3);
    var lineCount = 0;
    var done = false;
    while (!done && (inBuffer[0] = readBase64()) != END_OF_INPUT){
        inBuffer[1] = readBase64();
        inBuffer[2] = readBase64();
        result += (base64Chars[ inBuffer[0] >> 2 ]);
        if (inBuffer[1] != END_OF_INPUT){
            result += (base64Chars [(( inBuffer[0] << 4 ) & 0x30) | (inBuffer[1] >> 4) ]);
            if (inBuffer[2] != END_OF_INPUT){
                result += (base64Chars [((inBuffer[1] << 2) & 0x3c) | (inBuffer[2] >> 6) ]);
                result += (base64Chars [inBuffer[2] & 0x3F]);
            } else {
                result += (base64Chars [((inBuffer[1] << 2) & 0x3c)]);
                result += ('=');
                done = true;
            }
        } else {
            result += (base64Chars [(( inBuffer[0] << 4 ) & 0x30)]);
            result += ('=');
            result += ('=');
            done = true;
        }
        lineCount += 4;
        if (lineCount >= 76){
            result += ('\r\n');
            lineCount = 0;
        }
    }
    return result;
}

function kwencrypt(str){
	str=xxtea_encrypt64(str,token);
	return str;
}
function xxtea_encrypt64(str, key) {
	return encodeBase64(xxtea_encrypt(str, key));
}

function kwencrypts(p){
	if(p){
		for(var k in p){
			p[k] = kwencrypt(p[k]);
		}
	}
	return p;
}

function setBase64Str(str){
    base64Str = str;
    base64Count = 0;
}
function readBase64(){    
    if (!base64Str) return END_OF_INPUT;
    if (base64Count >= base64Str.length) return END_OF_INPUT;
    var c = base64Str.charCodeAt(base64Count) & 0xff;
    base64Count++;
    return c;
}
function encodeBase64(str){
    setBase64Str(str);
    var result = '';
    var inBuffer = new Array(3);
    var lineCount = 0;
    var done = false;
    while (!done && (inBuffer[0] = readBase64()) != END_OF_INPUT){
        inBuffer[1] = readBase64();
        inBuffer[2] = readBase64();
        result += (base64Chars[ inBuffer[0] >> 2 ]);
        if (inBuffer[1] != END_OF_INPUT){
            result += (base64Chars [(( inBuffer[0] << 4 ) & 0x30) | (inBuffer[1] >> 4) ]);
            if (inBuffer[2] != END_OF_INPUT){
                result += (base64Chars [((inBuffer[1] << 2) & 0x3c) | (inBuffer[2] >> 6) ]);
                result += (base64Chars [inBuffer[2] & 0x3F]);
            } else {
                result += (base64Chars [((inBuffer[1] << 2) & 0x3c)]);
                result += ('=');
                done = true;
            }
        } else {
            result += (base64Chars [(( inBuffer[0] << 4 ) & 0x30)]);
            result += ('=');
            result += ('=');
            done = true;
        }
        lineCount += 4;
        if (lineCount >= 76){
            result += ('\r\n');
            lineCount = 0;
        }
    }
    return result;
}

function kwbms(p){
	if(p){
		for(var k in p){
			p[k] = kwbm(p[k]);
		}
	}
	return p;
}
function kwbm(str){
	if(str && jc_isencode && jc_isencode=="Y"){
		str = str.replace(/[\u00B7|\u4E00-\u9FA5|\uFF00-\uFF20|\u2014|\u201c|\u3000-\u303F|\u2160-\u216b|\uFF21-\uFFEE|\u2460-\u24FF|\u25a0-\u25FF|\u2267|\u201d\u201c]/gm, function(){
			return "&#" + arguments[0].charCodeAt(0) + ";";
		});
	}
	return str;
}

function kwfilters(p){
	if(p){
		for(var k in p){
			p[k]=kwfilter(p[k]);
		}
	}
	return p;
}
function kwfilter(str){
	if(str){
		str = str.replace(/\+/g,"_abc123");
		str = str.replace(/\-/g,"_def456");
		str = str.replace(/\=/g,"_ghi789");
		str = str.replace(/\//g,"_jkl098");
		str = str.replace(/\*/g,"_mno765");
	}
	return str;
}

