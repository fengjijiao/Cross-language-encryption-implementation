var forge = require('node-forge');
function encrypt(text) {
  var key = this.forge.util.hexToBytes("310bc65ed2bfd049a3877465ebd547210a492db4edf03e9506a3f942135d55e4")
  var iv = this.forge.util.hexToBytes("000000000000000000000000")
  var cipher = this.forge.cipher.createCipher('AES-GCM', key);
  cipher.start({
    iv: iv, // should be a 12-byte binary-encoded string or byte buffer
    //additionalData: 'binary-encoded string', // optional
    //tagLength: 128 // optional, defaults to 128 bits
  });
  cipher.update(this.forge.util.createBuffer(text));
  cipher.finish();
  var encrypted = cipher.output;
  //var tag = cipher.mode.tag;
  // outputs encrypted hex
  console.log(encrypted.toHex());
  // outputs authentication tag
  //console.log(tag.toHex());
  //var hex = forge.util.bytesToHex(bytes)
  return encrypted;
}

function decrypt(text) {
  var key = this.forge.util.hexToBytes("310bc65ed2bfd049a3877465ebd547210a492db4edf03e9506a3f942135d55e4")
  var iv = this.forge.util.hexToBytes("000000000000000000000000")
  // decrypt some bytes using GCM mode
  var decipher = forge.cipher.createDecipher('AES-GCM', key);
  decipher.start({
    iv: iv,
    //additionalData: 'binary-encoded string', // optional
    //tagLength: 128, // optional, defaults to 128 bits
    //tag: tag // authentication tag from encryption
  });
  decipher.update(encrypted);
  var pass = decipher.finish();
  // pass is false if there was a failure (eg: authentication tag didn't match)
  if(pass) {
    // outputs decrypted hex
    console.log(decipher.output.toHex());
    return decipher.output;
  }
  return null;
}
