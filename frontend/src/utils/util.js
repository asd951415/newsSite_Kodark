
const Util = function() {}

// http response header에 links 헤더를 파싱하는 함수
// ex) '</en/users/sign-up>; rel="self", </en/signIn>; rel="next"'
Util.prototype.parseLinks = function(links){
  return links.replace(/ |<|>|rel=|"|'/g, '').split(',').map(link => link.split(';')).reduce((ac, cu) => {
    const [link, rel] = cu
    ac[rel] = link
    return ac
  },{})
}

export default Util;
